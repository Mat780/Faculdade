public class Comida extends Atividade {
	private double precoMedioRodizio;

	public Comida(String nome, double precoMedioRodizio){
		super(nome);
		setPrecoMedioRodizio(precoMedioRodizio);
	}

	private void setPrecoMedioRodizio(double precoMedioRodizio) {
		this.precoMedioRodizio = precoMedioRodizio;
	}

	public double getPrecoMedioRodizio() {
		return precoMedioRodizio;
	}
}
