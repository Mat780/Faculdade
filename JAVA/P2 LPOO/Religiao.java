public class Religiao extends Atividade {
	private int frequenciaRecomendada;

	public Religiao(String nome, int frequenciaRecomendada){
		super(nome);
		setFrequenciaRecomendada(frequenciaRecomendada);
	}

	private void setFrequenciaRecomendada(int frequencia) {
		if(frequencia >= 0){
			this.frequenciaRecomendada = frequencia;
			
		} else {
			System.out.println("Frequencia negativa não aceita");
		}
	}

	public int getFrequenciaRecomendada() {
		return frequenciaRecomendada;
	}

}
