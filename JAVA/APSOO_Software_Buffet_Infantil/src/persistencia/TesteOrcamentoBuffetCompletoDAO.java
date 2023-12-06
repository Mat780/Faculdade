package persistencia;
import java.util.ArrayList;

import negocio.Bolo;
import negocio.Cliente;
import negocio.Data;
import negocio.Doce;
import negocio.OrcamentoBuffetCompleto;
import negocio.Pagamento;
import negocio.Salgado;

public class TesteOrcamentoBuffetCompletoDAO {

	public static void main(String[] args) {
		System.out.println(ConexaoBanco.getConexao());
		System.out.println(ConexaoBanco.status);
		
		OrcamentoBuffetCompletoDAO orcDAO = new OrcamentoBuffetCompletoDAO();
		ClienteDAO cliDAO = new ClienteDAO();
		
		//criar
		Data data = new Data(13,11,2023);
		Pagamento pagamento = new Pagamento(100.23,"debito",1);
		
		Salgado a = new Salgado(1,"quibe",2.2);
		Salgado b = new Salgado(2,"coxinha",2.4);
		
		ArrayList<Salgado> salgadoList = new ArrayList<Salgado>();
		salgadoList.add(a);
		salgadoList.add(b);
		
		Doce x = new Doce(1,"brigadeiro",2.4);
		Doce y = new Doce(2, "bala", 3.4);
		
		ArrayList<Doce> doceList = new ArrayList<Doce>();
		doceList.add(x);
		doceList.add(y);
		
		Bolo bolo = new Bolo(1,"Prestigio",100);
		bolo.setPeso(2.0);
		
		Cliente cliente = cliDAO.getAll().get(0);
		
		System.out.println(cliente.getRg());
		
		OrcamentoBuffetCompleto orc = new OrcamentoBuffetCompleto(10,"10:30",data,pagamento,cliente,1,false,salgadoList,doceList,bolo);
		orcDAO.criar(orc);
		
		//buscar
		
		OrcamentoBuffetCompleto orc2 = orcDAO.get(1);
		System.out.println("resultado busca: \n"+ orc2.getHoraDeInicio());
		
		//atualizar 
		
		ArrayList<Salgado> listaSalgados = OrcamentoBuffetCompleto.getArraySalgadoAssociados(orc2.getId());
		ArrayList<Doce> listaDoces = OrcamentoBuffetCompleto.getArrayDoceAssociados(orc2.getId());
		
//		OrcamentoBuffetCompleto orc3 = new OrcamentoBuffetCompleto(10, "11:30", orc2.getData(), orc2.getPagamento(), orc2.getCliente(),orc2.getId() ,orc2.getTeraCerveja(), listaSalgados, listaDoces, orc2.getBolo());
//		
//		orcDAO.atualizar(orc3);
		
		//deletar
		
//		orcDAO.deletar(orc3.getId());
		
	}

}
