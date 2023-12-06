package persistencia;
import negocio.Usuario;

public class TesteUsuarioDAO {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(ConexaoBanco.getConexao());
		System.out.println(ConexaoBanco.status);
		
		
		//criar
		UsuarioDAO dao = new UsuarioDAO();
		Usuario u = new Usuario("pedro","senha1234","Pedro Paulo","admin");
		
		dao.criar(u);
		
		//buscar
		Usuario result = dao.get("pedro");
		System.out.println("Resultado busca: "+result.getSenha());
		
		//atualizar
		Usuario u2 = new Usuario(u.getLogin(),null,u.getNome(),u.getTipoDeUsuario());
		
		dao.atualizar(u2);
		
//		dao.deletar("pedro");
	}

}
