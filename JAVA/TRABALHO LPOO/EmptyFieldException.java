public class EmptyFieldException extends RuntimeException {
	public EmptyFieldException(){
		super("Todos os campos devem estar preenchidos");
	}
}
