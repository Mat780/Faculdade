gcc:
	gcc -o wcat wcat.c -Wall -Werror
	gcc -o wgrep wgrep.c -Wall -Werror
	gcc -o wzip wzip.c -Wall -Werror
	gcc -o wunzip wunzip.c -Wall -Werror

executarWindows: gcc
	wcat Lorem.txt
    
	wgrep A Teste.txt
	wgrep linha TesteGrep.txt Teste.txt

	wzip Lorem.txt Teste.txt
	
	wunzip file.z

executarLinux: gcc
	./wcat Lorem.txt
    
	./wgrep A Teste.txt
	./wgrep linha TesteGrep.txt Teste.txt

	./wzip Lorem.txt Teste.txt
	
	./wunzip file.z