public class Youtuber extends Pessoa{
	private int horasSemanaisNoComputador; // Até 70 horas

	public Youtuber(String cpf, int horas){
		super(cpf, new Atividade[5]);
		setHorasSemanaisNoComputador(horas);
	}

	private void setHorasSemanaisNoComputador(int horas) {
		if(horas > 70){
			System.out.println("Esse youtuber excede o nosso limite de horas em frente ao computador por semana");
		} else {
			this.horasSemanaisNoComputador = horas;
		}
		
	}

	public int getHorasSemanaisNoComputador() {
		return horasSemanaisNoComputador;
	}

	@Override
	public void calcularValorAtividades() {
		System.out.printf("\n%s%s\n" , "Calculando o valor das atividades do Youtuber de CPF: ", getCpf());

		Atividade[] listaAtividades = getListaAtividades();
		int valorFinal = 0;
		int valorTemp = 0;

		for (int c = 0; c < listaAtividades.length ; c++){
			valorTemp = 0;

			if(listaAtividades[c] instanceof Esporte){
				Esporte AtvEsporte = (Esporte) listaAtividades[c];
				
				if(AtvEsporte.getIsGroup()){
					valorTemp += 20;

				} else {
					valorTemp += 10;
				}

				if(AtvEsporte.getCustoHora() <= 50.00){
					valorTemp += 10;
				}

				System.out.printf("%s%s%s%d\n", "O esporte \"", AtvEsporte.getNome(), "\" possui valor: ", valorTemp);

			} else if (listaAtividades[c] instanceof Comida){
				Comida AtvComida = (Comida) listaAtividades[c];

				if("sushi".equals(AtvComida.getNome().toLowerCase())){
					valorTemp += 80;

				} else if(AtvComida.getPrecoMedioRodizio() <= 39.90){
					valorTemp += 25;

				} else {
					valorTemp += 10;
				}

				System.out.printf("%s%s%s%d\n", "A comida \"", AtvComida.getNome(), "\" possui valor: ", valorTemp);

			} else if (listaAtividades[c] instanceof Religiao){
				Religiao AtvReligiao = (Religiao) listaAtividades[c];

				if(AtvReligiao.getFrequenciaRecomendada() <= 1){
					valorTemp += 15;

				} else {
					valorTemp += 5;
				}

				System.out.printf("%s%s%s%d\n", "A religião \"", AtvReligiao.getNome(), "\" possui valor: ", valorTemp);
			} else { // Caso não haja nenhuma atividade
				break;
			}

			valorFinal += valorTemp;

		} // Fim do for
		
		if(valorFinal == 0) {
			System.out.println("\nNenhuma atividade cadastrada");
		} else {
			System.out.printf("\n%s%d\n", "Valor final das atividades deste Youtuber: ", valorFinal);
		}

	} // Fim da função

	

}
