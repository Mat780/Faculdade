package persistencia;

import negocio.ServicoAdicional;

public class TesteServicoAdicionalDAO {
	public static void main(String args[])
	{
		System.out.print(ConexaoBanco.getConexao());
		System.out.print(ConexaoBanco.status);
		
		ServicoAdicional servicoAdicional = new ServicoAdicional(1050124796, "Copeira", 120.00, false);
		
		ServicoAdicionalDAO servicoAdicionalDAO = new ServicoAdicionalDAO();
		
//		servicoAdicionalDAO.criar(servicoAdicional);
		System.out.println(servicoAdicionalDAO.getAll());
		System.out.println(servicoAdicionalDAO.get(1050124796));
//		System.out.println(servicoAdicionalDAO.atualizar(servicoAdicional));
		System.out.println(servicoAdicionalDAO.deletar(1050124796));
	}
}
