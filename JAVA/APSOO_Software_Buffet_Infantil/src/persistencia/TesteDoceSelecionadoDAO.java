package persistencia;
import negocio.Doce;
import negocio.DoceSelecionado;

public class TesteDoceSelecionadoDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(ConexaoBanco.getConexao());
		System.out.print(ConexaoBanco.status);
		
		DoceSelecionadoDAO doceSelDAO = new DoceSelecionadoDAO();
		DoceDAO doceDAO = new DoceDAO();
		
		//criar
		
		Doce a = doceDAO.getAll().get(0);
		
		DoceSelecionado doceSel = new DoceSelecionado(2,a);
		
		doceSelDAO.criarBuffet(doceSel, 1);
		
		//buscar
		
		System.out.println(doceSelDAO.getDoce(1, 1).getDoce().getDescricao());
		
		//Buscar todos os Doces
		
		System.out.println(doceSelDAO.getAllBuffet(1).get(0).getDescricao());
		
		//Atualizar
		
		DoceSelecionado b = new DoceSelecionado(10,a);
		
		doceSelDAO.atualizarBuffet(b, 1);
		
		//deletar
		
		doceSelDAO.deletarDoce(1,1);
	}

}
