#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <bits/stdc++.h>
#include <mpi.h> /* Include MPI's header file */

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

int binarySearch(int arr[], int l, int r, int x)
{
    while (l <= r) {
        int m = l + (r - l) / 2;
 
        // Check if x is present at mid
        if (arr[m] == x)
            return m;
 
        // If x greater, ignore left half
        if (arr[m] < x)
            l = m + 1;
 
        // If x is smaller, ignore right half
        else
            r = m - 1;
    }
 
    return -1;
}

/* The IncOrder function that is called by qsort is defined as follows */
int IncOrder(const void *e1, const void *e2){
    return (*((int *)e1) - *((int *)e2));
}

int *SampleSort(int n, int *elmnts, int *nsorted, MPI_Comm comm, bool buscaBinaria, bool introSort) {
  
    int i, j, nlocal, npes, myrank;
    int *sorted_elmnts, *splitters, *allpicks;
    int *scounts, *sdispls, *rcounts, *rdispls;

    /* Get communicator-related information */
    MPI_Comm_size(comm, &npes);
    MPI_Comm_rank(comm, &myrank);
    nlocal = n/npes;

    /* Allocate memory for the arrays that will store the splitters */
    splitters = (int *)malloc(npes*sizeof(int));
    allpicks = (int *)malloc(npes*(npes-1)*sizeof(int));

    /* Sort local array */
    if (introSort) Introsort(elmnts, elmnts, elmnts + nlocal - 1);
    else qsort(elmnts, nlocal, sizeof(int), IncOrder);

    /* Select local npes-1 equally spaced elements */
    for (i=1; i<npes; i++)
        splitters[i-1] = elmnts[i*nlocal/npes];
  
    /* Gather the samples in the processors */
    MPI_Allgather(splitters, npes-1, MPI_INT, allpicks, npes-1,
        MPI_INT, comm);
  
    /* sort these samples */

    if (introSort) Introsort(allpicks, allpicks, allpicks + npes*(npes-1) - 1);
    else qsort(allpicks, npes*(npes-1), sizeof(int), IncOrder);

    /* Select splitters */
    for (i=1; i<npes; i++)
        splitters[i-1] = allpicks[i*npes];
    splitters[npes-1] = INT_MAX;
  
    /* Compute the number of elements that belong to each bucket */
    if (buscaBinaria) {
        //* Nova implementação: Busca binária
        scounts = (int *)malloc(npes*sizeof(int));
        int left = 0;
        int right = n-1;
        int positionInArray = 0;
        for (i = j = 0; i < npes; i++) {
            scounts[i] = 0;
            positionInArray = binarySearch(elmnts, left, right, splitters[j++]);

            if (positionInArray != -1) {
                left = positionInArray;
                scounts[i] = positionInArray;
            }
        }
    } else {
        //* Antiga Implementação: Percorrer o vetor inteiro   
        scounts = (int *)malloc(npes*sizeof(int));
        for (i=0; i<npes; i++)
            scounts[i] = 0;

        for (j = i = 0; i<nlocal; i++) {
            if (elmnts[i] < splitters[j])
                scounts[j]++;
            else
                scounts[++j]++;
        }
    }

    /* Determine the starting location of each bucket's elements in the elmnts array */
    sdispls = (int *)malloc(npes*sizeof(int));
    sdispls[0] = 0;

    for (i=1; i<npes; i++)
        sdispls[i] = sdispls[i-1]+scounts[i-1];

    /* Perform an all-to-all to inform the corresponding processes of the number of elements */
    /* they are going to receive. This information is stored in rcounts array */

    rcounts = (int *)malloc(npes*sizeof(int));
    MPI_Alltoall(scounts, 1, MPI_INT, rcounts, 1, MPI_INT, comm);

    /* Based on rcounts determine where in the local array the data from each processor */
    /* will be stored. This array will store the received elements as well as the final */
    /* sorted sequence */
    
    rdispls = (int *)malloc(npes*sizeof(int));
    rdispls[0] = 0;

    for (i=1; i<npes; i++)
        rdispls[i] = rdispls[i-1]+rcounts[i-1];

    *nsorted = rdispls[npes-1]+rcounts[i-1];
    sorted_elmnts = (int *)malloc((*nsorted)*sizeof(int));

    /* Each process sends and receives the corresponding elements, using the MPI_Alltoallv */
    /* operation. The arrays scounts and sdispls are used to specify the number of elements */
    /* to be sent and where these elements are stored, respectively. The arrays rcounts */
    /* and rdispls are used to specify the number of elements to be received, and where these */
    /* elements will be stored, respectively. */
    MPI_Alltoallv(elmnts, scounts, sdispls, MPI_INT, sorted_elmnts, rcounts, rdispls, MPI_INT, comm);

    /* Perform the final local sort */
    if (introSort) Introsort(sorted_elmnts, sorted_elmnts, sorted_elmnts + (*nsorted) - 1);
    else qsort(sorted_elmnts, *nsorted, sizeof(int), IncOrder);
    
    free(splitters); 
    free(allpicks); 
    free(scounts); 
    free(sdispls);

    free(rcounts); 
    free(rdispls);
    return sorted_elmnts;
}

int main(int argc, char *argv[]) {
    int n;
    int npes;
    int myrank;
    int nlocal;
    int *elmnts; /* array that stores the local elements */
    int *vsorted;  /* array that stores the final sorted elements */
    int nsorted; /* number de elements in vsorted */
    int i;
    MPI_Status status;
    double stime, etime;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &npes);
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank);

    n = pow(10, atoi(argv[1]));
    nlocal = n/npes; /* Compute the number of elements to be stored locally. */

    /* Allocate memory for the various arrays */
    elmnts = (int *)malloc(nlocal*sizeof(int));
    
    bool buscaBinaria, introSort;

    if (argv[2] == "T") buscaBinaria = true;
    else buscaBinaria = false;

    if (argv[3] == "T") introSort = true;
    else introSort = false;

    /* Fill-in the elmnts array with random elements */
    srand48(time(NULL) + myrank);

    for (i=0; i<nlocal; i++)
        elmnts[i] = lrand48()%(10*n+1);

    MPI_Barrier(MPI_COMM_WORLD);
    stime = MPI_Wtime();
    vsorted = SampleSort(n, elmnts, &nsorted, MPI_COMM_WORLD, buscaBinaria, introSort);

    etime = MPI_Wtime();
    
    MPI_Barrier(MPI_COMM_WORLD);

    printf("tempo = %.5lf\n", etime-stime);
    
    free(elmnts);
    free(vsorted);
    
    MPI_Finalize();
    
    return 0;
}