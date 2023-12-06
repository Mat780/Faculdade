package excecoes;

public class ExcecaoOrcamentoParaHoje extends RuntimeException {
	public ExcecaoOrcamentoParaHoje() {
		super("Um orçamento não pode ser fechado para o mesmo dia de sua contratação");
	}
}
