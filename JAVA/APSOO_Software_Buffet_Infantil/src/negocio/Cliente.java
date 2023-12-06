package negocio;

import java.util.ArrayList;

import excecoes.ExcecaoDDDInvalido;
import excecoes.ExcecaoParametroPreenchidoErrado;
import excecoes.ExcecaoSomenteLetras;
import excecoes.ExcecaoSomenteNumeros;
import persistencia.ClienteDAO;
import utilitaria.Utilitaria;

public class Cliente {

	private String nome; 				// OBRIGATORIO
	private String cpf; 				// OBRIGATORIO
	private String rg; 					// OPCIONAL
	private String endereco;			// OBRIGATORIO
	private String cep; 				// OBRIGATORIO
	private String celular; 			// OBRIGATORIO
	private String telefoneResidencial; // OPCIONAL
	private String telefoneComercial; 	// OPCIONAL
	private String email; 				// OBRIGATORIO

	/* Construtor minimo */
	public Cliente(String nome, String cpf, String endereco, String cep, String celular, String email)
		throws ExcecaoSomenteLetras, 
		ExcecaoSomenteNumeros, 
		ExcecaoParametroPreenchidoErrado, 
		ExcecaoDDDInvalido {
		
		setNome(nome);
		setCpf(cpf);
		setEndereco(endereco);
		setCep(cep);
		setCelular(celular);
		setEmail(email);

	}

	// Construtor Completo
	public Cliente(String nome, String cpf, String endereco, String cep, 
				   String celular, String email, String rg, String telefoneResidencial, String telefoneComercial)
		throws ExcecaoSomenteLetras, 
		ExcecaoSomenteNumeros, 
		ExcecaoParametroPreenchidoErrado, 
		ExcecaoDDDInvalido {
		
		setNome(nome);
		setCpf(cpf);
		setRg(rg);
		setEndereco(endereco);
		setCep(cep);
		setCelular(celular);
		setTelefoneResidencial(telefoneResidencial);
		setTelefoneComercial(telefoneComercial);
		setEmail(email);
	}

	private void setNome(String nome) throws ExcecaoSomenteLetras {
		if (Utilitaria.verificarNome(nome) == false) throw new ExcecaoSomenteLetras("Nome");
		this.nome = nome;
	}
	
	private void setCpf (String cpf) throws ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarCPF(cpf) == false) throw new ExcecaoParametroPreenchidoErrado("CPF");
		this.cpf = cpf;
	}
	
	public void setRg (String rg) throws ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarRG(rg) == false) throw new ExcecaoParametroPreenchidoErrado("RG");
		this.rg = rg;
	}

	private void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	private void setCep(String cep) throws ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarCEP(cep) == false) throw new ExcecaoParametroPreenchidoErrado("CEP");
		this.cep = cep;
	}

	private void setCelular(String celular) throws ExcecaoDDDInvalido, ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarCelular(celular) == false) throw new ExcecaoParametroPreenchidoErrado("Celular");
		this.celular = celular;
	}

	public void setTelefoneResidencial(String telefoneResidencial) throws ExcecaoDDDInvalido, ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarTelefoneFixo(telefoneResidencial) == false) throw new ExcecaoParametroPreenchidoErrado("Telefone residencial");
		this.telefoneResidencial = telefoneResidencial;
	}
	
	public void setTelefoneComercial(String telefoneComercial) throws ExcecaoDDDInvalido, ExcecaoParametroPreenchidoErrado {
		if (Utilitaria.verificarCelular(telefoneComercial) == false) throw new ExcecaoParametroPreenchidoErrado("Telefone comercial");
		this.telefoneComercial = telefoneComercial;
	}

	private void setEmail(String email) {
		if (Utilitaria.verificarEmail(email) == false) throw new ExcecaoParametroPreenchidoErrado("Email");
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCep() {
		return cep;
	}

	public String getCelular() {
		return celular;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public String getEmail() {
		return email;
	}

	public static Cliente getCliente(String cpf) {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.get(cpf);
	}

	public static void deletarCliente(String cpf) {
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.deletar(cpf);
	}

	public static void atualizarCliente(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.atualizar(cliente);
	}

	public static boolean cadastrarCliente(Cliente cliente) {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.criar(cliente);
	}

	public static ArrayList<Cliente> getAllCliente() {
		ClienteDAO clienteDAO = new ClienteDAO();
		return clienteDAO.getAll();
	}

}
