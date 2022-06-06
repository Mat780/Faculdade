public class Politico extends Pessoa {
	private double salario;


	public Politico(String cpf, double salario) {
		super(cpf, new Atividade[10]);
		setSalario(salario);
	}

	private void setSalario(double salario) {
		if(salario > 30000){
			System.out.println("Teto salarial atingido, um politico não pode ganhar mais que R$30.000");
		} else {
			this.salario = salario;
		}
		
	}

	public double getSalario() {
		return salario;
	}

	@Override
	public void calcularValorAtividades() {
		System.out.printf("\n%s%s\n" , "Calculando o valor das atividades do Politico de CPF: ", getCpf());

		Atividade[] listaAtividades = getListaAtividades();
		int valorFinal = 0;
		int valorTemp = 0;

		for (int c = 0; c < listaAtividades.length; c++) {
			valorTemp = 0;

			if(listaAtividades[c] instanceof Esporte){
				Esporte AtvEsporte = (Esporte) listaAtividades[c];

				if(AtvEsporte.getIsGroup()){
					valorTemp += 5;
				} else {
					valorTemp += 20;
				}

				System.out.printf("%s%s%s%d\n", "O esporte \"", AtvEsporte.getNome(), "\" possui valor: ", valorTemp);

			} else if (listaAtividades[c] instanceof Comida){
				Comida AtvComida = (Comida) listaAtividades[c];

				if(AtvComida.getPrecoMedioRodizio() >= 150.00){
					valorTemp += 40;
				} else {
					valorTemp += 5;
				}

				System.out.printf("%s%s%s%d\n", "A comida \"", AtvComida.getNome(), "\" possui valor: ", valorTemp);

			} else if (listaAtividades[c] instanceof Religiao){
				Religiao AtvReligiao = (Religiao) listaAtividades[c];

				switch(AtvReligiao.getFrequenciaRecomendada()){
					case 1:
					case 2:
						valorTemp += 10;
						break;
					case 3: 
					case 4: 
						valorTemp += 20;
						break;
					case 5:
					case 6:
						valorTemp += 30;
						break;
				}

				System.out.printf("%s%s%s%d\n", "A religião \"", AtvReligiao.getNome(), "\" possui valor: ", valorTemp);

			} else {
				break;
			}

			valorFinal += valorTemp;
			
		} // Fim do switch
		
		if(valorFinal == 0) {
			System.out.println("\nNenhuma atividade cadastrada");
		} else {
			System.out.printf("\n%s%d\n", "Valor final das atividades deste Youtuber: ", valorFinal);
		}

	} // Fim da função

}
