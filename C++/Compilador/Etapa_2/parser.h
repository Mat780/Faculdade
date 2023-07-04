#include "scanner.h"

class Parser
{
	private:
		Scanner* scanner;
		Token* tokenAtual;

		// Guardamos um token a frente do atual, para algumas verificações
		// essas garantem a execução esperada do analisador.
		Token* lookForwardToken;

		// Funções para o funcionamento do parser
		void advance();
		bool match(int);
		bool matchAhead(int);
        
		// Produções abaixo
		void PROGRAM();
        void MAIN_CLASS();
		bool CLASS_DECLARATION();
		bool VAR_DECLARATION();
		bool METHOD_DECLARATION();
        bool OP();
		bool TYPE();
		bool EXPRESSION();
		bool STATEMENT();
		bool EXPRESSION_LINHA();

		// Função para erro durante a análise sintática, 
		// essa função encerra o programa
        void error(string, string);
		
	public:
		Parser(string);
		void run();
};