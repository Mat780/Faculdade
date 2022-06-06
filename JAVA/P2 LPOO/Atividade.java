public abstract class Atividade {
	private String nome;

	public Atividade(String nome) {
		setNome(nome);
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
