#include "parser.h"

// Construtor da classe Parser
Parser::Parser(string input) {
	scanner = new Scanner(input);
}

// Função de avanço do parser
void Parser::advance() {
	tokenAtual = lookForwardToken;
	lookForwardToken = scanner->nextToken();
}

// Função para ver se o token passado como parametro "casa" com o tokenAtual
// Caso coincida então ele avançará para o proximo token e retornará verdadeiro
// Caso contrário irá retornar falso somente.
bool Parser::match(int t) {
	if (tokenAtual->name == t || tokenAtual->attribute == t) {
		advance();
		return true;
	} 
	else return false;
}

// Função para ver se o token passado como parametro "casa" com o lookForwardToken
// Caso coincida então retornará verdadeiro
// Caso contrário irá retornar falso somente.
bool Parser::matchAhead(int t) {
	if (lookForwardToken->name == t || lookForwardToken->attribute == t) return true;
	else return false;
}

// Função para fazer o Parser rodar a analize sintática
void Parser::run() {

	// Pega o primeiro Token para o lookforward
	advance();

	// Passa o Token de lookforward para o tokenAtual
	advance();	

	// Inicia o processo de analise sintatica
	PROGRAM();
	
	cout << "Compilacao encerrada com sucesso!\n";
}

// Produção inicial da nossa análise
void Parser::PROGRAM() {
    MAIN_CLASS();

	while(CLASS_DECLARATION());

	if (match(FIM_DE_ARQUIVO) == false) error("FIM_DE_ARQUIVO", enumToString(tokenAtual->name)); 
}

// Produção MAIN_CLASS da nossa gramatica
void Parser::MAIN_CLASS() {
	if (match(CLASS)) {
		if (match(ID) == false) error("ID", enumToString(tokenAtual->name));

		if (match(ABRE_CHAVES) == false) error("ABRE_CHAVES", enumToString(tokenAtual->name));

		if (match(PUBLIC) == false) error("PUBLIC", enumToString(tokenAtual->name));

		if (match(STATIC) == false) error("STATIC", enumToString(tokenAtual->name));
		
		if (match(VOID) == false) error("VOID", enumToString(tokenAtual->name));
		
		if (match(MAIN) == false) error("MAIN", enumToString(tokenAtual->name));

		if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

		if (match(STRING) == false) error("STRING", enumToString(tokenAtual->name));

		if (match(ABRE_COLCHETE) == false) error("ABRE_COLCHETE", enumToString(tokenAtual->name));

		if (match(FECHA_COLCHETE) == false) error("FECHA_COLCHETE", enumToString(tokenAtual->name));

		if (match(ID) ==  false) error("ID", enumToString(tokenAtual->name));

		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

		if (match(ABRE_CHAVES) == false) error("ABRE_CHAVES", enumToString(tokenAtual->name));		

		if (STATEMENT() == false) error("fim do STATEMENT", "retorno falso");

		if (match(FECHA_CHAVES) == false) error("FECHA_CHAVES", enumToString(tokenAtual->name));

		if (match(FECHA_CHAVES) == false) error("FECHA_CHAVES", enumToString(tokenAtual->name));

	} else error("CLASS", enumToString(tokenAtual->name));
}

// Produção CLASS_DECLARATION da nossa gramatica
bool Parser::CLASS_DECLARATION() {
	if (match(CLASS)) {

		if (match(ID) == false) error("ID", enumToString(tokenAtual->name));

		// Parte opcional da gramatica, por isso caso não haja extends não significa que há um erro
		if (match(EXTENDS)) {
			if (match(ID) == false) error("ID", enumToString(tokenAtual->name));
		}

		if (match(ABRE_CHAVES) == false) error("ABRE_CHAVES", enumToString(tokenAtual->name));

		// Os dois whiles abaixo servem para ter várias ou nenhuma das duas produções chamadas nos whiles
		while (VAR_DECLARATION());
		while (METHOD_DECLARATION());
		
		if (match(FECHA_CHAVES) == false) error("FECHA_CHAVES", enumToString(tokenAtual->name));

		return true;
	}

	return false;
}

// Produção VAR_DECLARATION da nossa gramatica
bool Parser::VAR_DECLARATION() {

	// Comparação para que não haja conflito entre a VAR_DECLARATION
	// E a produção: Statement -> ID = Expression;
	// Caso o lookForwardToken conter uma atribuição então estamos buscando a produção acima
	// E não uma declaração de variavel
	if (matchAhead(ATRIBUICAO)) {
		return false;
	}

	if (TYPE()) { 
		if (match(ID) == false) error("ID", enumToString(tokenAtual->name)); 
		if (match(PONTO_VIRGULA) == false) error("PONTO_VIRGULA", enumToString(tokenAtual->name));
		return true;
	} 

	else return false;
}

// Produção METHOD_DECLARATION da nossa gramatica
bool Parser::METHOD_DECLARATION() {
	if (match(PUBLIC)) {
		if (TYPE() == false) error("fim do TYPE", "retorno falso");

		if (match(ID) == false) error("ID", enumToString(tokenAtual->name));

		if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

		if (TYPE()) {
			if (match(ID) == false) error("ID", enumToString(tokenAtual->name));

			// Esta parte pode ocorrer nenhuma ou várias vezes
			while(match(VIRGULA)) {
				if (TYPE() == false) error("fim do TYPE", "retorno falso");
				if (match(ID) == false) error("ID", enumToString(tokenAtual->name));
			}
		}

		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

		if (match(ABRE_CHAVES) == false) error("ABRE_CHAVES", enumToString(tokenAtual->name));

		// Os dois whiles abaixo servem para ter várias ou nenhuma das duas produções chamadas nos whiles
		while(VAR_DECLARATION());
		while(STATEMENT());
		
		if (match(RETURN) == false) error("RETURN", enumToString(tokenAtual->name));

		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

		if (match(PONTO_VIRGULA) == false) error("PONTO_VIRGULA", enumToString(tokenAtual->name));

		if (match(FECHA_CHAVES) == false) error("FECHA_CHAVES", enumToString(tokenAtual->name));

		return true;

	} 
	
	return false;
}

// Produção TYPE da nossa gramatica
bool Parser::TYPE() {

	if (match(INT)) {
		if (match(ABRE_COLCHETE)) {
			if (match(FECHA_COLCHETE) == false) error("FECHA_COLCHETE", enumToString(tokenAtual->name));
		} 
		
		goto tipoDeclarado;
	}

	else if (match(BOOLEAN)) goto tipoDeclarado;

	// Primeiro verificamos se há um '[' no lookForwardToken, se não houver logo verificamos se o tokenAtual é um ID
	// Fazemos isso para evitar que dê problemas com a produção: Statement -> ID[Expression] = Expression; 
	else if (matchAhead(ABRE_COLCHETE) == false) 
		if (match(ID)) goto tipoDeclarado;

	return false;

	tipoDeclarado: return true;
}

// Produção STATEMENT da nossa gramatica
bool Parser::STATEMENT() {
	if(match(ABRE_CHAVES)) {

		// O while abaixo servem para ter várias ou nenhuma da produção chamada no while
		while(STATEMENT());

		if (match(FECHA_CHAVES) == false) error("FECHA_CHAVES", enumToString(tokenAtual->name));

		goto statementFeito;
	}
	
	else if (match(IF)) {
		if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

		if (STATEMENT() == false) error("fim do STATEMENT", "retorno falso");

		if (match(ELSE) == false) error("ELSE", enumToString(tokenAtual->name));

		if (STATEMENT() == false) error("fim do STATEMENT", "retorno falso");

		goto statementFeito;

	}
	
	else if (match(WHILE)) {
		if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");
		
		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

		if (STATEMENT() == false) error("fim do STATEMENT", "retorno falso");

		goto statementFeito;
	}
	
	else if (match(SYSTEM_OUT_PRINTLN)) {
		if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

		if (match(PONTO_VIRGULA) == false) error("PONTO_VIRGULA", enumToString(tokenAtual->name));

		goto statementFeito;

	}
	
	else if (match(ID)) {

		// Parte opcional da gramatica, portanto se não houver não significa que é um erro
		if (match(ABRE_COLCHETE)) {
			if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");
			if (match(FECHA_COLCHETE) == false) error("FECHA_COLCHETE", enumToString(tokenAtual->name));
		}	

		if (match(ATRIBUICAO) == false) error("ATRIBUICAO", enumToString(tokenAtual->name));

		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");	

		if (match(PONTO_VIRGULA) == false) error("PONTO_VIRGULA", enumToString(tokenAtual->name));

		goto statementFeito;
	}
	
	return false;

	statementFeito: return true;

}

// Produção EXPRESSION da nossa gramatica
bool Parser::EXPRESSION() {
	if (match(NUMERO)) {
		EXPRESSION_LINHA();
		goto expressionFeito;
	}
			
	else if (match(TRUE)) {
		EXPRESSION_LINHA();
		goto expressionFeito;
	}
			
	else if (match(FALSE)) {
		EXPRESSION_LINHA();
		goto expressionFeito;
	}
	
	else if (match(ID)) {
		EXPRESSION_LINHA();
		goto expressionFeito;
	}
	
	else if (match(THIS)) {
		EXPRESSION_LINHA();
		goto expressionFeito;
	}
	
	else if (match(NEW)) {
		if (match(INT)) {
			if (match(ABRE_COLCHETE) == false) error("ABRE_COLCHETE", enumToString(tokenAtual->name));
			
			if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

			if (match(FECHA_COLCHETE) == false) error("FECHA_COLCHETE", enumToString(tokenAtual->name));
			
			EXPRESSION_LINHA();
		}
		else if (match(ID)) {
			if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));
			
			if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

			EXPRESSION_LINHA();

		}
		else error("INT ou ID", enumToString(tokenAtual->name));

		goto expressionFeito;
	}
	else if (match(NEGACAO)) {
		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");
		
		EXPRESSION_LINHA();

		goto expressionFeito;
	}
	else if (match(ABRE_PARENTESES)) {
		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");
		
		if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));
		
		EXPRESSION_LINHA();

		goto expressionFeito;
	} 
	else {
		; // Epsilon
	}

	return false;

	expressionFeito: return true;
	
}

// Produção EXPRESSION_LINHA da nossa gramatica
bool Parser::EXPRESSION_LINHA() {
	if (OP()) {
		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

		EXPRESSION_LINHA();

		goto expressionLinhaFeito;
	}

	else if (match(ABRE_COLCHETE)) {
		if (EXPRESSION() == false) error("fim do EXPRESSION", "retorno falso");

		if (match(FECHA_COLCHETE) == false) error("FECHA_COLCHETE", enumToString(tokenAtual->name));

		EXPRESSION_LINHA();

		goto expressionLinhaFeito;
	}

	else if (match(PONTO_FINAL)) {

		if (match(LENGTH)) EXPRESSION_LINHA();

		else if (match(ID)) {
			if (match(ABRE_PARENTESES) == false) error("ABRE_PARENTESES", enumToString(tokenAtual->name));

			if (EXPRESSION()) {
				while(match(VIRGULA)) if (EXPRESSION() == false) error("VIRGULA", enumToString(tokenAtual->name));
			}	
			
			if (match(FECHA_PARENTESES) == false) error("FECHA_PARENTESES", enumToString(tokenAtual->name));

			EXPRESSION_LINHA();

		}
		else error("LENGTH ou ID", enumToString(tokenAtual->name));

		goto expressionLinhaFeito;
	}

	else {
		; // Epsilon
	}

	return false;

	expressionLinhaFeito: return true;
}

// Produção OP da nossa gramatica
bool Parser::OP() {
	if (match(E_LOGICO)) goto opFeito;

	else if (match(MENOR_Q)) goto opFeito;

	else if (match(MAIOR_Q)) goto opFeito;

	else if (match(IGUALDADE)) goto opFeito;

	else if (match(DIFERENTE)) goto opFeito;

	else if (match(ADICAO)) goto opFeito;

	else if (match(SUBTRACAO)) goto opFeito;

	else if (match(MULTIPLICACAO)) goto opFeito;

	else if (match(DIVISAO)) goto opFeito;

	return false;

	opFeito: return true;
}

// Função para reportar um erro que ocorreu durante a analise sintática
void Parser::error(string esperado, string recebido) {
	cout << "Erro na Linha " << scanner->getLinhaAtual() << endl;
	cout << "Esperado: " << esperado << ", Recebido: " << recebido << endl; 

	exit(EXIT_FAILURE);
}

