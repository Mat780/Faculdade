package excecoes;

public class ExcecaoSomenteLetras extends RuntimeException {

	private static final long serialVersionUID = -8448086836910709329L;
	
	public ExcecaoSomenteLetras(String msg){
		super("O campo "+ msg + " pode ter apenas letras.");
	}
}
