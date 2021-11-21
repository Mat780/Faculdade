#include <stdio.h>
#include <stdlib.h>

const char* filename = "exemplo.2.cap";

int main(int argc, char *argv[])
{
    FILE *in_file = fopen(filename, "r");


    char file_contents[4];

    while (fscanf(in_file, " %s", file_contents) != EOF) {

		if (file_contents[0] == '#'){
			fscanf(in_file, "%*[^\n] ");
			
		} else {
			printf("> %s\n", file_contents);
		}
        
    }

    fclose(in_file);
    exit(EXIT_SUCCESS);
}