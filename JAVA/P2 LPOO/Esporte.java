public class Esporte extends Atividade {
	private boolean isGroup;
	private double custoHora;

	public Esporte(String nome, boolean isGroup, double custoHora){
		super(nome);
		setIsGroup(isGroup);
		setCustoHora(custoHora);
	}

	private void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	private void setCustoHora(double custoHora) {
		this.custoHora = custoHora;
	}

	public boolean getIsGroup() {
		return isGroup;
	}

	public double getCustoHora() {
		return custoHora;
	}
}
