public class InvalidPassportException extends Exception {
	public InvalidPassportException(){
		super("O seu passaporte não está atualizado, portanto você não pode viajar para o exterior");
	}
}
