package persistencia;
import negocio.Cliente;

public class TesteClienteDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(ConexaoBanco.getConexao());
		System.out.print(ConexaoBanco.status);
	

		Cliente c = new Cliente("Jonas da Silva", "88149699066", "Rua Gumercindo Alves", "12345-678", "67 99988-1235",
				"jonasgames123@gmail.com", "267584288", "67 9999-4444", "67 88888-4545");

		ClienteDAO cDao = new ClienteDAO();

		cDao.criar(c);

		System.out.println("Resultado busca: " + cDao.get(c.getCpf()).getNome());

		Cliente c3 = new Cliente("Joao", c.getCpf(), "Rua Aguas Claras", c.getCep(), c.getCelular(), c.getEmail(),
				c.getRg(), c.getTelefoneResidencial(), c.getTelefoneComercial());

		cDao.atualizar(c3);
		System.out.println(c3.getCpf());

		System.out.println("Resultado busca: " + cDao.get(c3.getCpf()).getNome());
		
		cDao.deletar(c3.getCpf());
		
		System.out.println(cDao.getAll());
	}

}
