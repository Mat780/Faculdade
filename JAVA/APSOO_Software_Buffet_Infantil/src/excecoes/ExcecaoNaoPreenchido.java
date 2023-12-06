package excecoes;

public class ExcecaoNaoPreenchido extends RuntimeException {
	
	private static final long serialVersionUID = 7003279388703833013L;

	public ExcecaoNaoPreenchido(String msg) {
		super("O campo "+ msg + " não está preenchido!");
	}
}
