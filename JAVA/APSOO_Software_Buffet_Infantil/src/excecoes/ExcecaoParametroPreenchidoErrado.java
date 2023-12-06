package excecoes;

public class ExcecaoParametroPreenchidoErrado extends RuntimeException {
	
	private static final long serialVersionUID = 4386200303229996836L;

	public ExcecaoParametroPreenchidoErrado(String msg){
		super("O atributo "+ msg + " está preenchido incorretamente.");
	}
}
