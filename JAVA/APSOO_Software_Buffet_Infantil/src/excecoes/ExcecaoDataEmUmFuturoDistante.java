package excecoes;

public class ExcecaoDataEmUmFuturoDistante extends RuntimeException {
	public ExcecaoDataEmUmFuturoDistante() {
		super("A data para este orçamento está em um futuro distante, maior que o estipulado pelas regras de negocio");
	}
}
