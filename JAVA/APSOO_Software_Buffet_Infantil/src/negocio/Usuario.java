package negocio;

public class Usuario {
	private String login;
	private String senha;
	private String nome;
	private String tipoDeUsuario;
	
	public Usuario(String login, String senha, String nome, String tipoDeUsuario) {
		setLogin(login);
		setSenha(senha);
		setNome(nome);
		setTipoDeUsuario(tipoDeUsuario);
	}
	
	private void setLogin(String login) {
		this.login = login;
	}
	
	private void setSenha(String senha) {
		this.senha = senha;
	}
	
	private void setNome(String nome) {
		this.nome = nome;
	}
	
	private void setTipoDeUsuario(String tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTipoDeUsuario() {
		return tipoDeUsuario;
	}

}
