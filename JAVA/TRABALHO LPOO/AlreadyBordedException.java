
public class AlreadyBordedException extends RuntimeException {
	public AlreadyBordedException(String message) {
		super("O " + message + " já está em uma equipe de bordo");
	}
}
