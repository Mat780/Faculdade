package excecoes;

public class ExcecaoValorNaoSetado extends RuntimeException {

	private static final long serialVersionUID = -7014168543486626922L;

	public ExcecaoValorNaoSetado(String nomeDaClasse, String qualAtributo) {
		super("O objeto: " + nomeDaClasse + " não teve seu atributo " + qualAtributo + " definido, portanto esta operação é inválida.");
	}
	
}
