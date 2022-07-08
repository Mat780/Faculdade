
public class AlreadyContractedException extends RuntimeException {
	public AlreadyContractedException(){
		super("A equipe de bordo selecionada já está contratada para outro avião");
	}
}
