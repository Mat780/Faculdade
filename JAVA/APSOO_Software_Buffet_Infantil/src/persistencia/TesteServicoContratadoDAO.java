package persistencia;

import negocio.ServicoAdicional;
import negocio.ServicoContratado;

public class TesteServicoContratadoDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				System.out.print(ConexaoBanco.getConexao());
				System.out.print(ConexaoBanco.status);

				ServicoContratadoDAO servConDAO = new ServicoContratadoDAO();
				ServicoAdicionalDAO servDAO = new ServicoAdicionalDAO();
				
				//criar
				
				ServicoAdicional a = servDAO.getAll().get(0);
				
				System.out.println("Servico: " + a.getNome());
				
				ServicoContratado servCon = new ServicoContratado(2,a);
				
				servConDAO.criarServCon(servCon,1658430682);
				
//				//buscar
//				
//				System.out.println(servConDAO.getAll().get(0).getQuantidade());
//				
//				
//				//Atualizar
//				
//				ServicoContratado b = new ServicoContratado(10,a);
//				
//				servConDAO.atualizar(b);
				
				//deletar
				
//				servConDAO.deletar(3,1);
	}

}
