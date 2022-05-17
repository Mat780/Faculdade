O programa para ser executado e assim testado, deve ser compilado e linkdinado primeiro.

Para compilação e linkdinação: gcc *.c -g -o programa
Esse comando compilará e vai linkedinar os .h e .c, criando um executavel chamado programa

Este programa.exe deve ser executado atraves de um terminal, pois após a tecla Enter ser apertada ele irá:
Processar a expressão e printar seu resultado e em seguida fechar, por isso o uso de um terminal é necessário
Pois só com ele poderá ver o resultado, já que somente usando o executavel ele fechará antes mesmo de você conseguir ver o resultado

ATENÇÃO: O programa somente aceita expressões do tipo: 
( NUMERO OPERADOR NUMERO )
( ( NUMERO OPERADOR NUMERO ) OPERADOR ( NUMERO OPERADOR NUMERO ) ) 
E suas repetições...

Numeros aceitos: >= 0 , Ou seja numeros positivos (Isso só se aplica a entrada, pois a saida pode sim ser um numero negativo)

Operadores aceitos: + , - , * e / 

A saida do programa pode ser um numero de ponto flutuante positivo ou negativo,
depender de como a expressão for feita