public class InvalidTaskException extends Exception {
	public InvalidTaskException(String invalidTask){
		super("A tarefa " + invalidTask + " não é válida para este sistema");
	}
}