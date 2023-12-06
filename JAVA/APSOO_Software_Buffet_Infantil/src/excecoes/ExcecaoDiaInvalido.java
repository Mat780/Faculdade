package excecoes;

public class ExcecaoDiaInvalido extends RuntimeException {

	private static final long serialVersionUID = -3643461526573933188L;

	public ExcecaoDiaInvalido() {
        super("Ao registrar uma data, foi verificado que o dia está inválido!");
    }
}