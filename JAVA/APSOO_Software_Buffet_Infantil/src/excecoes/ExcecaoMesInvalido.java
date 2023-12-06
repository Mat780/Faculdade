package excecoes;

public class ExcecaoMesInvalido extends RuntimeException {

	private static final long serialVersionUID = -2609895937221037876L;

	public ExcecaoMesInvalido() {
        super("Ao registrar uma data, foi verificado que o mês está inválido.");
    }

}