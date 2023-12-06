package persistencia;
import negocio.Salgado;
import negocio.SalgadoSelecionado;

public class TesteSalgadoSelecionadoDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(ConexaoBanco.getConexao());
		System.out.print(ConexaoBanco.status);

		SalgadoSelecionadoDAO salgSelDAO = new SalgadoSelecionadoDAO();
		SalgadoDAO salgDAO = new SalgadoDAO();
		
		//criar
		
		Salgado a = salgDAO.getAll().get(1);
		
		SalgadoSelecionado salgSel = new SalgadoSelecionado(2,a);
		
		salgSelDAO.criarBuffet(salgSel, 1);
		
		//buscar
		
		System.out.println(salgSelDAO.getSalgado(3, 1).getSalgado().getDescricao());
		
		//Buscar todos os salgados
		
		System.out.println(salgSelDAO.getAllBuffet(1).get(0).getDescricao());
		
		//Atualizar
		
		SalgadoSelecionado b = new SalgadoSelecionado(10,a);
		
		salgSelDAO.atualizarBuffet(b, 1);
		
		//deletar
		
		salgSelDAO.deletarSalgado(3,1);
	}

}
