package utilitaria;

import excecoes.ExcecaoDDDInvalido;

public class Utilitaria {

	private Utilitaria() {
		throw new IllegalAccessError();
	}

	public static boolean verificarNome(String nome) {
		return nome.matches("[a-zA-Z]([ a-zA-Zçãõ])*+");
	}
	
	public static boolean verificarNumeroConvidados(int numeroDeConvidados) {
		return 50 <= numeroDeConvidados && numeroDeConvidados <= 180;
	}
	
	public static boolean verificarValidezHorario(String horaDeInicio) {
		String[] horario = horaDeInicio.split(":");

		int horaOk = Integer.parseInt(horario[0].strip());
		int minutoOk = Integer.parseInt(horario[1].strip());

		return (11 <= horaOk && horaOk <= 20 && 0 <= minutoOk && minutoOk <= 59);
	}

	public static boolean verificarOpcoesSalgados(int qtdDeSalgados) {
		return qtdDeSalgados >= 7;
	}

	public static boolean verificarOpcoesDoces(int qtdDeDoces) {
		return qtdDeDoces == 3;
	}

	public static boolean verificarCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (cpf.equals("00000000000") || cpf.equals("11111111111") ||
				cpf.equals("22222222222") || cpf.equals("33333333333") ||
				cpf.equals("44444444444") || cpf.equals("55555555555") ||
				cpf.equals("66666666666") || cpf.equals("77777777777") ||
				cpf.equals("88888888888") || cpf.equals("99999999999") ||
				cpf.length() != 11)
			return (false);

		char digitoDez, digitoOnze;
		int soma, resto, auxiliar, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			soma = 0;
			peso = 10;
			for (int i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				auxiliar = (int) (cpf.charAt(i) - 48);
				soma = soma + (auxiliar * peso);
				peso = peso - 1;
			}

			resto = 11 - (soma % 11);
			if ((resto == 10) || (resto == 11))
				digitoDez = '0';

			else
				digitoDez = (char) (resto + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			soma = 0;
			peso = 11;
			for (int i = 0; i < 10; i++) {
				auxiliar = (int) (cpf.charAt(i) - 48);
				soma = soma + (auxiliar * peso);
				peso = peso - 1;
			}

			resto = 11 - (soma % 11);
			if ((resto == 10) || (resto == 11))
				digitoOnze = '0';

			else
				digitoOnze = (char) (resto + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			return (digitoDez == cpf.charAt(9)) && (digitoOnze == cpf.charAt(10));

		} catch (Exception erro) {
			return (false);
		}
	}

	public static boolean verificarDDD(String ddd) {
		return ddd.matches("(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])");
	}

	public static boolean verificarCelular(String celular) throws ExcecaoDDDInvalido {
		if (celular.length() != 13)
			return false;

		String[] separacao = celular.split(" ");

		String ddd = separacao[0];
		String numero = separacao[1];

		boolean isDDDOk = verificarDDD(ddd);

		if (isDDDOk == false)
			throw new ExcecaoDDDInvalido();

		return numero.matches("\\d{5}-\\d{4}");
	}

	public static boolean verificarTelefoneFixo(String telefoneFixo) throws ExcecaoDDDInvalido {
		if (telefoneFixo.length() != 12)
			return false;

		String[] separacao = telefoneFixo.split(" ");

		String ddd = separacao[0];
		String numero = separacao[1];

		boolean isDDDOk = verificarDDD(ddd);

		if (isDDDOk == false)
			throw new ExcecaoDDDInvalido();

		return numero.matches("\\d{4}-\\d{4}");

	}

	public static boolean verificarRG(String rg) {

		if (rg == null || rg.equals("")) {
            return false;
        }
 
        for (int i = 0; i < rg.length(); i++)
        {
            char c = rg.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;

	}
	
	public static boolean verificarCEP(String cep) {
		return cep.matches("\\d{5}-\\d{3}");
	}

	public static boolean verificarEmail(String email) {
		return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]+");
	}
	
}
