from bs4 import BeautifulSoup
from threading import Thread
from multiprocessing import cpu_count
import requests
import os
from dotenv import load_dotenv
from src.server.instance import server

cache = server.cache

# My acess key from Aws
load_dotenv()
ACESS_KEY = os.getenv('ACESS_KEY')
SECRET_KEY = os.getenv('SECRET_KEY')
USER_AGENT = os.getenv('USER_AGENT')

HEADERS = ({'User-Agent': USER_AGENT, 'Accept-Language': 'pt-br;q=0.5', 'x-amz-access-key': ACESS_KEY,
            'x-amz-secret-key': SECRET_KEY})


# Function to extract Product Title
def get_title(soup):
    try:
        # Outer Tag Object
        title = soup.find("a", attrs={"data-hook": 'product-link'})

        # Inner NavigatableString Object
        title_value = title.text

        # Title as a string value
        title_string = title_value.strip()

    except AttributeError:
        title_string = ""

    return title_string


def get_review_comments(soup):
    array_of_comments = []
    try:
        review_comment = soup.find_all("span", attrs={"class": "cr-original-review-content"})

        for review in review_comment:
            array_of_comments.append(review.string.strip())

    except AttributeError:
        pass

    return array_of_comments


def get_reviews(soup):
    review_comments = get_review_comments(soup)
    return review_comments


# * This function can be used later for paging, when searching for products, and only that by the moment
def get_next_page_of_search(soup):
    page = soup.find('ul', attrs={'class': 'a-pagination'})

    if not page.find('li', attrs={'class': 'a-disabled a-last'}):
        url = str(page.find('li', attrs={'class': 'a-last'}).find('a')['href'])
        return url

    else:
        return


def get_next_page_of_reviews(soup):
    next_page = soup.find("ul", attrs={'class': 'a-pagination'})

    if next_page is not None:
        next_page = next_page.find("li", attrs={'class': 'a-last'})

    else:
        next_page = None

    return next_page


def get_all_links_from_search_page(URL):
    # HTTP Request
    webpage = requests.get(URL, headers=HEADERS)

    # Soup Object containing all data
    soup = BeautifulSoup(webpage.content, "html.parser")

    # Fetch links as List of Tag Objects
    links = soup.find_all("a", attrs={'class': 'a-link-normal s-no-outline'})

    # Store the links
    links_list = []

    # Loop for extracting links from Tag Objects
    for link in links:
        links_list.append(link.get('href'))
        if len(links_list) > 5:
            return links_list
    return links_list


def get_link_to_review_page_from_product_page(link):
    webpage = requests.get("https://www.amazon.com/" + link, headers=HEADERS)

    soup = BeautifulSoup(webpage.content, "html.parser")

    link_to_review = soup.find("a", attrs={'data-hook': 'see-all-reviews-link-foot'})

    if link_to_review is not None:
        return link_to_review.get('href')

    else:
        return None


def by_product(link_to_review):
    counter_page = 1
    next_page = not None
    avaliacoes = []
    new_soup = ''
    produto = ''
    index_ref = link_to_review.find('ref') - 1
    index_product_reviews = link_to_review.find('s') + 2
    product_id = link_to_review[index_product_reviews: index_ref]
    codigo = product_id.split("/")[-1]

    while (next_page is not None) and counter_page < 30:
        url = f"https://www.amazon.com/reviews/{codigo}?pageNumber={counter_page}&pageSize=10&sortBy=recent"

        webpage = requests.get(url, params=counter_page, headers=HEADERS)

        new_soup = BeautifulSoup(webpage.content, "html.parser")

        review_comments = get_reviews(new_soup)

        for review in review_comments:
            avaliacoes.append(review)
        next_page = get_next_page_of_reviews(new_soup)
        counter_page += 1
        if len(produto) < 1:
            produto = get_title(new_soup)
    return produto, avaliacoes


def mine_reviews_from_the_reviews_page(array_of_link_to_reviews, dictionary):
    for link_to_review in array_of_link_to_reviews:
        produto, avaliacoes = by_product(link_to_review)
        dictionary['produto'].append(produto)
        dictionary['avaliacoes'].append(avaliacoes)
        dictionary['link'].append(link_to_review)
    return dictionary


def mine_from_the_search_page(produto):
    dictionary = {"produto": [], "avaliacoes": [], 'link': []}
    review_link_list = []

    # A partir daqui Multi-thread liberado

    URL = f"https://www.amazon.com/s?k={produto}"

    links_list = get_all_links_from_search_page(URL)

    for link in links_list:

        review_link = get_link_to_review_page_from_product_page(link)

        if review_link is not None:
            review_link_list.append(review_link)

    threads = []
    number_of_cpus = cpu_count()

    partition = len(review_link_list) // number_of_cpus

    for i in range(number_of_cpus):

        if i == 0:
            threads.append(
                Thread(target=mine_reviews_from_the_reviews_page, args=(review_link_list[:partition], dictionary)))

        elif i == number_of_cpus - 1:
            threads.append(
                Thread(target=mine_reviews_from_the_reviews_page, args=(review_link_list[partition:], dictionary)))

        else:
            threads.append(Thread(target=mine_reviews_from_the_reviews_page,
                                  args=(review_link_list[partition: partition * 2], dictionary)))

        partition += partition

    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()

    # ! Debug
    return dictionary
