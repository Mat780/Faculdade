import axios from 'axios';

export default async function link(link) {
    return await axios.post(`http://localhost:5000/link`,
        {
            link: link,
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });
}