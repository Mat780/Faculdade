package controladoras;

import excecoes.ExcecaoDDDInvalido;
import negocio.Bolo;
import negocio.Cliente;
import negocio.Data;
import negocio.Doce;
import negocio.OrcamentoBuffetCompleto;
import negocio.Salgado;
import utilitaria.Utilitaria;

import java.util.ArrayList;

public class ControladoraOrcamentoDeBuffetCompleto {

	private ControladoraOrcamentoDeBuffetCompleto() throws IllegalAccessException {
		throw new IllegalAccessException("Instanciamento inválido, esta classe é estática");
	}

	public static double consultarValorDoOrcamento(OrcamentoBuffetCompleto orcamento) {
		return orcamento.calcularValorTotal();
	}

	public static void cadastrarOrcamento(OrcamentoBuffetCompleto orcamentoBuffetCompleto) {
		 orcamentoBuffetCompleto.cadastrarOrcamento();
	}

	public static OrcamentoBuffetCompleto criarOrcamento(int numeroDeConvidados, int numeroDeColaboradores, Data dataDoEvento,
			Cliente cliente, String horaDeInicio, boolean teraCerveja, ArrayList<Salgado> opcoesDeSalgado,
			ArrayList<Doce> opcoesDeDoces, Bolo opcaoDeBolo) {
		// necessario incluir Pagamento nos parametros
		return new OrcamentoBuffetCompleto(numeroDeConvidados, numeroDeColaboradores, horaDeInicio, dataDoEvento, null,
				cliente, -1, teraCerveja, opcoesDeSalgado, opcoesDeDoces, opcaoDeBolo);
	}

	public static boolean verificarNumeroDeConvidados(int numeroDeConvidados) {
		return Utilitaria.verificarNumeroConvidados(numeroDeConvidados);
	}

	public static boolean verificarData(Data data) {
		return OrcamentoBuffetCompleto.verificarData(data);
	}

	public static boolean verificarValidezHorario(String horaDeInicio) {
		return Utilitaria.verificarValidezHorario(horaDeInicio);
	}
	
	public static boolean verificarNomeCompleto(String nome) {
		return Utilitaria.verificarNome(nome);
	}
	
	public static boolean verificarCPF(String cpf) {
		return Utilitaria.verificarCPF(cpf);
	}
	
	public static boolean verificarEmail(String email) {
		return Utilitaria.verificarEmail(email);
	}
	
	public static boolean verificarCEP(String cep) {
		return Utilitaria.verificarCEP(cep);
	}
	
	public static boolean verificarCelular(String celular) throws ExcecaoDDDInvalido {
		return Utilitaria.verificarCelular(celular);
	}

	public static boolean verificarOpcoesSalgados(int qtdSalgados) {
		return Utilitaria.verificarOpcoesSalgados(qtdSalgados);
	}

	public static boolean verificarOpcoesDoces(int qtdDoces) {
		return Utilitaria.verificarOpcoesDoces(qtdDoces);
	}

	public static void cancelarOrcamento(OrcamentoBuffetCompleto orcamentoBuffet) {
		orcamentoBuffet.deletarOrcamento();
	}

	public static ArrayList<Salgado> getAllSalgados() {
		return OrcamentoBuffetCompleto.getAllSalgados();
	}

	public static ArrayList<Doce> getAllDoces() {
		return OrcamentoBuffetCompleto.getAllDoces();
	}

	public static ArrayList<Bolo> getAllBolos() {
		return OrcamentoBuffetCompleto.getAllBolos();
	}
}
