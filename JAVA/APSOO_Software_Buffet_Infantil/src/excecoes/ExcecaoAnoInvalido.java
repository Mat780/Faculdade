package excecoes;

public class ExcecaoAnoInvalido extends RuntimeException {

	private static final long serialVersionUID = 4324289896468756909L;

	public ExcecaoAnoInvalido(String msg) {
        super("Ao registrar uma data, foi verificado que o usuário é " + msg + ".");
    }
}