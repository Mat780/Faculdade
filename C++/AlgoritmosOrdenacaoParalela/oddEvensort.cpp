#include <mpi.h>
#include <stdio.h>
#include <bits/stdc++.h>
#include <iostream>
#include <string>
#include <fstream>
#include <unistd.h>

using namespace std;

void swapValue(int* a, int* b) {
	int* temp = a;
	a = b;
	b = temp;
	return;
}

/* Function to sort an array using insertion sort*/
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

// A function to partition the array and return
// the partition point
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

// A function that find the middle of the
// values pointed by the pointers a, b, c
// and return that pointer
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

	else // (*c <= *b && *b <= *a)
		return (b);
}

// A Utility function to perform intro sort
void IntrosortUtil(int arr[], int* begin, int* end,
				int depthLimit) {
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
	int* pivot = MedianOfThree(begin, begin + size / 2, end);

	// Swap the values pointed by the two pointers
	swapValue(pivot, end);

	// Perform Quick Sort
	int* partitionPoint = Partition(arr, begin - arr, end - arr);
	
    IntrosortUtil(arr, begin, partitionPoint - 1, depthLimit - 1);
	
    IntrosortUtil(arr, partitionPoint + 1, end, depthLimit - 1);

	return;
}

/* Implementation of introsort*/
void Introsort(int arr[], int* begin, int* end) {
	int depthLimit = 2 * log(end - begin);

	// Perform a recursive Introsort
	IntrosortUtil(arr, begin, end, depthLimit);

	return;
}

// A utility function ot print an array of size n
void printArray(int arr[], int n) {

    int id;
    MPI_Comm_rank(MPI_COMM_WORLD, &id);

	string texto = "/saidaDoProcessador_" + to_string(id) + ".txt";
	string path =  get_current_dir_name() + texto; // Aparentemente só funciona em linux essa função

	ofstream fw(path, std::ofstream::out);

	fw << "Olá sou o processador " << id << " e estes são os valores do meu vetor: " << endl;

	for (int i = 0; i < n; i++)
		fw << arr[i] << " \n"[i + 1 == n];
	
	fw.close();
}

void mergeArrs(int res[], int arr[], int recvbuf[], int n) {

	for (int i = 0; i < n; i++) 
		res[i] = arr[i]; 

	int counter = 0;

	for (int i = n; i < (2 * n); i++) 
		res[i] = recvbuf[counter++];

}

void compareAndSwap(int arr[], int n, int id, bool eMin, int iteration) {

	int *res = (int*)malloc(n * 2 * sizeof(int));
	int *recvbuf = (int*)malloc(n * sizeof(int));

	MPI_Sendrecv(arr, n, MPI_INT, id, iteration,
				recvbuf, n, MPI_INT, MPI_ANY_SOURCE, iteration, 
				MPI_COMM_WORLD, MPI_STATUS_IGNORE);

	mergeArrs(res, arr, recvbuf, n);
	Introsort(res, res, res + (2*n)-1);

	if (eMin) {
		for (int i = 0; i < n; i++) 
			arr[i] = res[i]; 

	} else {
		int counter = 0;
		for (int i = n; i < (2*n); i++)
			arr[counter++] = res[i];
		
	}

}

void oddEvenSort(int arr[], int n) {

    int id, processors;
	MPI_Comm_size(MPI_COMM_WORLD, &processors);
    MPI_Comm_rank(MPI_COMM_WORLD, &id);

    for(int i = 0; i < processors; i++) {
		
        if (i % 2 == 1) { // Impar
            if (id % 2 == 1) {
				if (id != processors - 1)
                	compareAndSwap(arr, n, id + 1, true, i);
            } else if (id != 0) {
				compareAndSwap(arr, n, id - 1, false, i);
            }
        }

        else if (i % 2 == 0) { // Par
            if (id % 2 == 0) {
				if (id != processors - 1 || processors % 2 == 0)
                	compareAndSwap(arr, n, id + 1, true, i);
            } else {
                compareAndSwap(arr, n, id - 1, false, i);
            }
        }

    }

}

int main(int argc, char *argv[]) {

	int n;
	
	n = pow(10, atoi(argv[1]));

    MPI_Init(&argc, &argv);

	int processadores;
	
	MPI_Comm_size(MPI_COMM_WORLD, &processadores);

	n = n / processadores;

	int *arrProcessor = (int*)malloc(n * sizeof(int)); 
	int id;

	double t1, t2; 
	t1 = MPI_Wtime(); 

	MPI_Comm_rank(MPI_COMM_WORLD, &id);
	srand48(time(NULL) + id);	
	
	for(int i = 0; i < n; i++)
		arrProcessor[i] = drand48()*100.0 * (drand48() * (id+1));

	oddEvenSort(arrProcessor, n);

	printArray(arrProcessor, n);
    
    MPI_Finalize();

	t2 = MPI_Wtime(); 
	cout << "A execução demorou: " << t2 - t1 << " segundos, para o processador: " << id << endl;

    return 0;
}