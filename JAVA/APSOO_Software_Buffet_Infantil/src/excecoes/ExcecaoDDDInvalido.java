package excecoes;

public class ExcecaoDDDInvalido extends RuntimeException{

	private static final long serialVersionUID = -5309385099322920250L;

	public ExcecaoDDDInvalido(){
		super("O DDD está inválido.");
	}

}
