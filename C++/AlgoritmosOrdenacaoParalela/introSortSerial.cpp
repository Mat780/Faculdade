#include <iostream>
#include <ctime>
#include <bits/stdc++.h>
using namespace std;

void swapValue(int* a, int* b) {
	int* temp = a;
	a = b;
	b = temp;
	return;
}

void InsertionSort(int arr[], int* begin, int* end) {
	// Get the left and the right index of the subarray
	// to be sorted
	int left = begin - arr;
	int right = end - arr;

	for (int i = left + 1; i <= right; i++) {
		int key = arr[i];
		int j = i - 1;

		/* Move elements of arr[0..i-1], that are
				greater than key, to one position ahead
				of their current position */
		while (j >= left && arr[j] > key) {
			arr[j + 1] = arr[j];
			j = j - 1;
		}
		arr[j + 1] = key;
	}

	return;
}

int* Partition(int arr[], int low, int high) {
	int pivot = arr[high]; // pivot
	int i = (low - 1); // Index of smaller element

	for (int j = low; j <= high - 1; j++) {
		// If current element is smaller than or
		// equal to pivot
		if (arr[j] <= pivot) {
			// increment index of smaller element
			i++;

			swap(arr[i], arr[j]);
		}
	}
	swap(arr[i + 1], arr[high]);
	return (arr + i + 1);
}

int* MedianOfThree(int* a, int* b, int* c) {
	if (*a < *b && *b < *c)
		return (b);

	if (*a < *c && *c <= *b)
		return (c);

	if (*b <= *a && *a < *c)
		return (a);

	if (*b < *c && *c <= *a)
		return (c);

	if (*c <= *a && *a < *b)
		return (a);

	if (*c <= *b && *b <= *a)
		return (b);
}

void IntrosortUtil(int arr[], int* begin, int* end, int depthLimit) {
	// Count the number of elements
	int size = end - begin;

	// If partition size is low then do insertion sort
	if (size < 16) {
		InsertionSort(arr, begin, end);
		return;
	}

	// If the depth is zero use heapsort
	if (depthLimit == 0) {
		make_heap(begin, end + 1);
		sort_heap(begin, end + 1);
		return;
	}

	// Else use a median-of-three concept to
	// find a good pivot
	int* pivot
		= MedianOfThree(begin, begin + size / 2, end);

	// Swap the values pointed by the two pointers
	swapValue(pivot, end);

	// Perform Quick Sort
	int* partitionPoint
		= Partition(arr, begin - arr, end - arr);
	IntrosortUtil(arr, begin, partitionPoint - 1,
				depthLimit - 1);
	IntrosortUtil(arr, partitionPoint + 1, end,
				depthLimit - 1);

	return;
}

void Introsort(int arr[], int* begin, int* end) {
	int depthLimit = 2 * log(end - begin);

	IntrosortUtil(arr, begin, end, depthLimit);

	return;
}

void printArray(int arr[], int n) {
	for (int i = 0; i < n; i++)
		cout << arr[i] << " \n"[i % 10 == 9];
}

// Atualmente 10^9
int n = 1000000000, arr[1000000000];

int main(int argc, char** argv) {

    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 10000;
    }

    time_t inicio, fim;

    time(&inicio);

	Introsort(arr, arr, arr + n - 1);

    time(&fim);

    double tempoGasto =  double(fim - inicio);

    printf("Tempo para ordenar %d elementos: %.5f", n, tempoGasto);

	return 0;
}