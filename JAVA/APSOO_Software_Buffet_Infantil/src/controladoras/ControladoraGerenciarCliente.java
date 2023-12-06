package controladoras;

import java.util.ArrayList;

import negocio.Cliente;

public class ControladoraGerenciarCliente {
	
	private ControladoraGerenciarCliente() {
		throw new IllegalAccessError("Controladora Gerenciar Cliente é estatica");
	}
	
	public static Cliente getCliente(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		return Cliente.getCliente(cpf);
	}

	public static void cadastrarCliente(Cliente cliente) {
		Cliente.cadastrarCliente(cliente);
	}

	public static Cliente criar(String nome, String cpf, String rg, String endereco, String cep, String celular,
			String telefoneResidencial, String telefoneComercial, String email) {
		return new Cliente(nome, cpf, endereco, cep, celular, email, rg, telefoneResidencial, telefoneComercial);
	}

	public static void atualizar(String nome, String cpf, String rg, String endereco, String cep, String celular,
			String telefoneResidencial, String telefoneComercial, String email) {
		Cliente cliente = new Cliente(nome, cpf, endereco, cep, celular, email, rg, telefoneResidencial,
				telefoneComercial);
		Cliente.atualizarCliente(cliente);
	}

	public static void deletar(String cpf) {
		Cliente.deletarCliente(cpf);
	}

	public static ArrayList<Cliente> getAllClientes() {
		return Cliente.getAllCliente();
	}
}
