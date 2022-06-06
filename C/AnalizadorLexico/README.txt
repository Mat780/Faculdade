Para compilar o analisador sintatico (parser.c) faça:
make

Para executar o analisador faça:
./parser <programa>
onde <programa> é o nome do arquivo contendo o programa em C- a ser analisado.
O código tem 4 programas exemplos (exemplo1.c,exemplo2.c,exemplo3.c e exemplo4.c). Apenas o exemplo1.c está sintaticamente correto.

O analisador mostra na saída o total de linhas processadas e uma mensagem de sucesso ou de erro. Observe que o analisador pára no primeiro erro encontrado.

Para entender o analisador léxico (lex.c) faça uso do programa avaliaLex.c. Para compilá-lo faça:
make -f makefile-lex

Para executar o avaliador de léxico faça:
./avalia-lex <programa>
onde <programa> é o arquivo contendo o programa a ser analisado lexicamente.

O avaliador mostrará na saída padrão, a sequência de palavras aceitas (terminais da gramática de C-) pelo analisador léxico.

-------------------------------
Há duas versões do analisador léxico: lex.c e lex2.c

O lex.c usa uma Tabela de Transições (chamada delta), que é uma matriz com uma linha por estado e uma coluna por símbolo do alfabeto. A vantagem desta versão é que se a linguagem mudar, basta mudar a tabela sem mudar o código.

Já o lex2.c não usa uma tabela de transições e codifica as transições diretamente no corpo da função lex(). A vantagem deste método é a rapidez, mas a desvantagem é a necessidade de alterar o código caso a linguagem aceita pelo AFD mude.

Para compilar o analisador sintático com a versão 2 do lex (lex2.c) faça:
make -f makefile2

Para executar o compilador faça:
./parser2 <programa>

Para avaliar a versão 2 do lex (lex2.c) faça:
make -f makefile-lex2

Para executar o avaliador do lexico faça:
./avalia-lex2 <programa>


