package negocio;

public abstract class Alimento {
	private int id;
	private String descricao;
	private double valorUnitario;
	
	protected Alimento(int id, String descricao, double valorUnitario) {
		setId(id);
		setDescricao(descricao);
		setValorUnitario(valorUnitario);
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	private void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
}
