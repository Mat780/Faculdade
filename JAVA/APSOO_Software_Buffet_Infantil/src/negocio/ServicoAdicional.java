package negocio;

public class ServicoAdicional {
	private int id;
	private String nome;
	private double valor;
	private boolean servicoUnico;
	
	public ServicoAdicional(int id, String nome, double valor, boolean servicoUnico) {
		setId(id);
		setNome(nome);
		setValor(valor);
		setServicoUnico(servicoUnico);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}
	
	private void setValor(double valor) {
		this.valor = valor;
	}

	private void setServicoUnico(boolean servicoUnico) {
		this.servicoUnico = servicoUnico;
	}
	
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public double getValor() {
		return valor;
	}

	public boolean getServicoUnico() {
		return servicoUnico;
	}

	@Override
	public String toString() {
		return nome + String.format(" (%.2f)", valor);
	}
}
