public class Main{

	/*
	 *  Autor: Matheus Felipe Alves Durães
	 * 	Professor: Samuel the best
	 * 	
	 * 	Conteudo: P2 de LPOO, refeita
	 * 	Data de entrega: Segunda (06/06/2022), as 7h da manhã
	 */

	public static void main(String[] args) {
		Leitor leitor = new Leitor();

		Pessoa[] vetorPessoas = new Pessoa[10];
		int qtdPessoas = 0;

		Atividade[] vetorAtividade = new Atividade[10];
		int qtdAtividades = 0;
		
		int opc;
		int opcSaida = 7;

		// Adicionando algumas atividades, caso o usuário não queira adicionar mais.
		vetorAtividade[0] = new Esporte("Pelada (Futebol de rua)", true, 12.00);
		vetorAtividade[1] = new Esporte("Golfe", false, 200.99);
		vetorAtividade[2] = new Comida("Miojo", 10.00);
		vetorAtividade[3] = new Comida("Lagosta", 200.50);
		vetorAtividade[4] = new Religiao("Religiao semanal", 1);
		vetorAtividade[5] = new Religiao("Religiao segunda-a-sexta", 5);
		qtdAtividades = 6; // Como foram adicionados 6 atividades então definimos a quantidade de atividades para 6.

		// Seguindo a mesma linha, vamos fazer 1 Youtuber e 1 Politico, para podermos usar sem precisar cadastrar logo de cara.
		vetorPessoas[0] = new Youtuber("00000000000", 65);
		vetorPessoas[1] = new Politico("11111111111", 25000);
		qtdPessoas = 2; // Adicionamos 2 pessoas portanto colocaremos o contador de pessoas em 2.

		// Note que tanto Youtuber quanto Politico não estão com atividades associadas,
		// Portanto no menu será necessário atribuir as atividades através do menu.
		
		do{
			System.out.println("\n----------------- Menu -----------------");
			System.out.println("[1] - Cadastrar uma atividade");
			System.out.println("[2] - Cadastrar uma pessoa");
			System.out.println("[3] - Atribuir uma atividade a uma pessoa");
			System.out.println("[4] - Listar pessoa(s)");
			System.out.println("[5] - Listar atividade(s)");
			System.out.println("[6] - Calcular o valor das atividades de uma pessoa");
			System.out.println("[7] - Finalizar o programa");
			System.out.println("----------------------------------------");
			System.out.printf("%s","Qual opção você deseja: ");

			opc = leitor.leInteger();

			switch(opc){
				case 1: // Cadastrar uma atividade.
					do{
						System.out.println("----------------------------------------");
						System.out.println("Qual tipo de atividade você quer cadastrar?");
						System.out.println("[1] - Esporte");
						System.out.println("[2] - Comida");
						System.out.println("[3] - Religiao");
						System.out.printf("%s", "Qual a sua escolha: ");
						opc = leitor.leInteger();

						if(opc < 1 && opc > 3){
							System.out.println("\nOpção inválida, tente novamente");
						}

					} while (opc < 1 || opc > 3);

					String nome;

					switch(opc){
						case 1: // Esporte.
							System.out.println("\nQual o nome do esporte?");
							nome = leitor.leString();

							System.out.println("\nSeu esporte é em grupo?");
							System.out.println("true - Sim ele é em grupo");
							System.out.println("false - Não, ele é individual");
							boolean isGroup = leitor.leBoolean();

							System.out.println("\nQual o custo da hora a ser paga por aula?");
							System.out.printf("%s", "Custa: R$");
							double custoHora = leitor.leDouble();

							vetorAtividade[qtdAtividades] = new Esporte(nome, isGroup, custoHora);
							System.out.printf("\n%s%s%s\n", "Esporte ", nome ," cadastrado com sucesso");
							break;
						
						case 2: // Comida
							System.out.println("\nQual o nome da comida?");
							nome = leitor.leString();

							System.out.printf("\n%s%s\n", "Qual o custo médio do rodizio de ", nome);
							System.out.printf("%s", "Custo: R$");
							double custoRodizio = leitor.leDouble();

							vetorAtividade[qtdAtividades] = new Comida(nome, custoRodizio);
							System.out.printf("\n%s%s%s\n", "Comida ", nome ," cadastrada com sucesso");

							break;

						case 3: // Religao.
							System.out.println("\nQual o nome da religião?");
							nome = leitor.leString();

							System.out.println("\nQual a frequência recomendada para esta religião?");
							System.out.printf("%s", "Frequência mínimo 0: ");
							int frequencia = leitor.leInteger();

							vetorAtividade[qtdAtividades] = new Religiao(nome, frequencia);
							System.out.printf("\n%s%s%s\n", "Religião ", nome ," cadastrada com sucesso");

							break;

					} qtdAtividades++;

					break;

				case 2:	// Cadastrar uma pessoa.
					do{
						System.out.println("----------------------------------------");
						System.out.println("Qual tipo de pessoa você quer cadastrar?");
						System.out.println("[1] - Youtuber");
						System.out.println("[2] - Politico");
						System.out.printf("%s", "Qual a sua escolha: ");
						opc = leitor.leInteger();

						if(opc < 1 && opc > 2){
							System.out.println("\nOpção inválida, tente novamente");
						}

					} while (opc < 1 || opc > 2);

					System.out.println("\nQual o CPF desta pessoa?");
					String cpf = leitor.leString();

					if(cpf.length() != 11){
						System.out.println("CPF inválido, tente novamente");
						System.out.println("Um CPF válido deve conter 11 numeros");
						break;
					}

					if(opc == 1){
						System.out.println("\nQuantas horas esse Youtuber passa na frente do Pc por semana? (Limite 70 horas)");
						int horasNoComputador = leitor.leInteger();

						if(horasNoComputador > 70){
							System.out.println("Esse youtuber passa tempo demais em frente ao computador!");
							System.out.println("Por isso infelizmente não pode ser cadastrado em nosso sistema");
							break;
						}

						vetorPessoas[qtdPessoas] = new Youtuber(cpf, horasNoComputador);
						System.out.println("\nYoutuber cadastrado com sucesso!");

					} else {
						System.out.println("\nQuanto é o salário deste Politico? (Teto salarial: R$30.000)");
						double salario = leitor.leDouble();

						if(salario > 30000){
							System.out.println("Teto salarial atingido, ou você digitou errado ou está cometendo algum crime!");
							System.out.println("Por via de duvidas não iremos cadastra-lo em nosso sistema.");
							break;
						}

						vetorPessoas[qtdPessoas] = new Politico(cpf, salario);
						System.out.println("\nPolitico cadastrado com sucesso!");
					}

					qtdPessoas++;
					
					break;
				
				case 3: // Atribuir uma atividade a uma pessoa
					System.out.println("\nPara atribuirmos uma atividade a uma pessoa");
					System.out.println("Primeiro vamos escolher qual pessoa receberá a atividade");
					System.out.println("\n------------ Listagem pessoas -----------");	

					int numPessoa;
					int numAtividade;

					for(int i = 0; i < qtdPessoas; i++){
						if(vetorPessoas[i] instanceof Youtuber){
							System.out.printf("%s%d%s%s%s\n", "[", i, "] - ", "Youtuber: ", vetorPessoas[i].getCpf());

						} else if(vetorPessoas[i] instanceof Politico) {
							System.out.printf("%s%d%s%s%s\n", "[", i, "] - ", "Politico: ", vetorPessoas[i].getCpf());
						}
					}

					do{
						System.out.printf("%s", "Numero da pessoa escolhida: ");
						numPessoa = leitor.leInteger();

						if(numPessoa < 0 || numPessoa >= qtdPessoas){
							System.out.printf("%s\n", " Opção inválida");
						} else {
							System.out.printf("%s\n", " Opção aceita");
						}

					} while(numPessoa < 0 || numPessoa >= qtdPessoas);

					System.out.println("\nAgora precismos escolher uma atividade");

					System.out.println("\n---------- Listagem atividades ----------");

					for (int i = 0; i < qtdAtividades; i++){
						if(vetorAtividade[i] instanceof Esporte){
							System.out.printf("%s%d%s%s%s\n" , "[", i, "] - ", "Esporte: ", vetorAtividade[i].getNome());
						} else if (vetorAtividade[i] instanceof Comida){
							System.out.printf("%s%d%s%s%s\n" , "[", i, "] - ", "Comida: ", vetorAtividade[i].getNome());
						} else if (vetorAtividade[i] instanceof Religiao){
							System.out.printf("%s%d%s%s%s\n", "[", i, "] - " , "Religião: ", vetorAtividade[i].getNome());
						}
					}

					do{
						System.out.printf("%s", "Numero da atividade escolhida: ");
						numAtividade = leitor.leInteger();

						if(numAtividade < 0 || numAtividade >= qtdAtividades){
							System.out.printf("%s\n", " Opção inválida");
						} else {
							System.out.printf("%s\n", " Opção aceita");
						}

					} while(numAtividade < 0 || numAtividade >= qtdAtividades);

					vetorPessoas[numPessoa].AddAtividade(vetorAtividade[numAtividade]);

					break;
				case 4: // Listar pessoas
					System.out.println("\n------------ Listagem pessoas -----------");	

					for(int i = 0; i < qtdPessoas; i++){
						if(vetorPessoas[i] instanceof Youtuber){
							System.out.printf("%s%s\n", "Youtuber: ", vetorPessoas[i].getCpf());

						} else if(vetorPessoas[i] instanceof Politico) {
							System.out.printf("%s%s\n", "Politico: ", vetorPessoas[i].getCpf());

						}
					}
					break;
				case 5: // Listar atividades
					System.out.println("\n---------- Listagem atividades ----------");

					for (int i = 0; i < qtdAtividades; i++){
						if(vetorAtividade[i] instanceof Esporte){
							System.out.printf("%s%s\n" , "Esporte: ", vetorAtividade[i].getNome());
						} else if (vetorAtividade[i] instanceof Comida){
							System.out.printf("%s%s\n" , "Comida: ", vetorAtividade[i].getNome());
						} else if (vetorAtividade[i] instanceof Religiao){
							System.out.printf("%s%s\n" , "Religião: ", vetorAtividade[i].getNome());
						}
					}
					break;
				case 6: // Calcular o valor das atividades de uma pessoa
					System.out.println("\nPara calcularmos o valor das atividades de uma pessoa, primeiro vamos escolher a pessoa");

					for(int i = 0; i < qtdPessoas; i++){
						if(vetorPessoas[i] instanceof Youtuber){
							System.out.printf("%s%d%s%s%s\n", "[", i, "] - ", "Youtuber: ", vetorPessoas[i].getCpf());

						} else if(vetorPessoas[i] instanceof Politico) {
							System.out.printf("%s%d%s%s%s\n", "[", i, "] - ", "Politico: ", vetorPessoas[i].getCpf());
						}
					}

					do{
						System.out.printf("%s", "Numero da pessoa escolhida: ");
						opc = leitor.leInteger();

						if(opc < 0 || opc >= qtdPessoas){
							System.out.printf("%s\n", "Opção inválida");
						} else {
							System.out.printf("%s\n", "Opção aceita");
						}

					} while(opc < 0 || opc >= qtdPessoas);

					vetorPessoas[opc].calcularValorAtividades();

					break;

				case 7: // Finalizar o programa
					System.out.println("Finalizando programa...");
					break;

				default:
					System.out.println("Opção inválida, tente novamente");
					break;
			}

			// Se o vetor de atividades estiver cheio
			if(qtdAtividades == vetorAtividade.length){
				Atividade[] atividadesTemp = new Atividade[qtdAtividades * 2];

				for(int i = 0; i < qtdAtividades; i++) atividadesTemp[i] = vetorAtividade[i];
				
				vetorAtividade = atividadesTemp;

			// Senão se o vetor de pessoas estiver cheio
			} else if (qtdPessoas == vetorPessoas.length) {
				Pessoa[] pessoasTemp = new Pessoa[qtdPessoas * 2];

				for(int i = 0; i < qtdPessoas; i++) pessoasTemp[i] = vetorPessoas[i];

				vetorPessoas = pessoasTemp;

			}

		// While opção não for a opção para sair do programa
		} while(opc != opcSaida);
		

	}
}