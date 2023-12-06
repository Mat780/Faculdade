package negocio;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import persistencia.OrcamentoLocacaoDeEspacoDAO;

public class OrcamentoLocacaoDeEspaco extends OrcamentoEvento {
	private ArrayList<ServicoContratado> arrayServicosContratados;
	
	public OrcamentoLocacaoDeEspaco(int numeroDeConvidados, String horaDeInicio, Data data, Cliente cliente) {
		super(-1, numeroDeConvidados, horaDeInicio, data, null, cliente);
		arrayServicosContratados = new ArrayList<>();
	}
	
	public OrcamentoLocacaoDeEspaco(int numeroDeConvidados, int numeroDeColaboradores, String horaDeInicio, Data data, Cliente cliente) {
		super(-1, numeroDeConvidados, numeroDeColaboradores, horaDeInicio, data, null, cliente);
		arrayServicosContratados = new ArrayList<>();
	}

	public ArrayList<ServicoContratado> getArrayServicosContratados() {
		return arrayServicosContratados;
	}
	
	public boolean cadastrarOrcamento() {
		OrcamentoLocacaoDeEspacoDAO orcamentoLocacaoDeEspacoDAO = new OrcamentoLocacaoDeEspacoDAO();
		
		boolean foiCadastrado = false;
		
		foiCadastrado = Cliente.cadastrarCliente(getCliente()); 
		Cliente.atualizarCliente(getCliente());
		
		if (foiCadastrado) {
			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
		} else {
			JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
		}
		
		if (orcamentoLocacaoDeEspacoDAO.criar(this)) {
			JOptionPane.showMessageDialog(null, "Orçamento cadastrado com sucesso!");
			foiCadastrado = true;
		} else {
			JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar o orçamento, ocorreu algum erro", "Erro: Cadastrar Orcamento", JOptionPane.ERROR_MESSAGE);
			foiCadastrado = false;
		}

		return foiCadastrado;
	}

	public void anexarServico(ServicoContratado servico) {
		arrayServicosContratados.add(servico);
	}
	
	@Override
	public double calcularValorTotal() {
		double valorTotal = 750.00;

		for (int i = 0; i < arrayServicosContratados.size(); i++) {
			valorTotal = valorTotal + arrayServicosContratados.get(i).getServicoAdicional().getValor() * arrayServicosContratados.get(i).getQuantidade();
		}
		
		return valorTotal;
	}

	public static boolean verificarData(Data data) {
		OrcamentoLocacaoDeEspacoDAO DAO = new OrcamentoLocacaoDeEspacoDAO();
		return DAO.verificarData(data);
	}

	@Override
	public boolean deletarOrcamento() {
		OrcamentoLocacaoDeEspacoDAO DAO = new OrcamentoLocacaoDeEspacoDAO();
		return DAO.deletar(getId());
	}
}
