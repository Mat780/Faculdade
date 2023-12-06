package persistencia;

import java.util.ArrayList;

import negocio.Cliente;
import negocio.Data;
import negocio.OrcamentoLocacaoDeEspaco;
import negocio.Pagamento;
import negocio.ServicoAdicional;
import negocio.ServicoContratado;

public class TesteOrcamentoLocacaoDeEspacoDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(ConexaoBanco.getConexao());
		System.out.println(ConexaoBanco.status);
		
		OrcamentoLocacaoDeEspacoDAO orcDAO = new OrcamentoLocacaoDeEspacoDAO();
		ClienteDAO cliDAO = new ClienteDAO();
		
		//criar
		Data data = new Data(13,11,2023);
		Pagamento pagamento = new Pagamento(100.23,"debito",1);
		
		ServicoAdicional servAd = new ServicoAdicional(10,"Lava Jato",100, false);
		ServicoAdicionalDAO serAdDAO = new ServicoAdicionalDAO();
		
		servAd.setId(736968052);
		serAdDAO.criar(servAd);
		
		ArrayList<ServicoContratado> servicos = new ArrayList<ServicoContratado>();
		ServicoContratado serv = new ServicoContratado(10,servAd);
		servicos.add(serv);
		
		Cliente cliente = new Cliente("Jonas da Silva", "88149699066", "Rua Gumercindo Alves", "12345-678", "67 99988-1235",
				"jonasgames123@gmail.com", "267584288", "67 9999-4444", "67 88888-4545");
//		cliDAO.criar(cliente);
		
		System.out.println(cliente.getRg());
		
		OrcamentoLocacaoDeEspaco orc = new OrcamentoLocacaoDeEspaco(10,10,"10:30",data,cliente);
		orcDAO.criar(orc);
		
//		//buscar
//		
//		OrcamentoLocacaoDeEspaco orc2 = orcDAO.get(1658430682);
//		System.out.println("resultado busca: \n"+ orc2.getHoraDeInicio());
		
//		//atualizar 
//		
//		
//		OrcamentoLocacaoDeEspaco orc3 = new OrcamentoLocacaoDeEspaco(12,7,"10:21",data,pagamento,orc2.getCliente(),orc2.getArrayServicosContratados());
//		
//		orcDAO.atualizar(orc3);
//		
//		//deletar
//		
//		orcDAO.deletar(orc3.getId());
	}

}
