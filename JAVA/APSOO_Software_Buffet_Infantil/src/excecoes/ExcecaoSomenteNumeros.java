package excecoes;

public class ExcecaoSomenteNumeros extends RuntimeException{

	private static final long serialVersionUID = 5267796468960397018L;

	public ExcecaoSomenteNumeros(String msg){
		super("O campo "+ msg + " pode ter apenas números.");
	}

}
