
public class EqualObjectException extends RuntimeException{
	public EqualObjectException(String message){
		super("Esse " + message + " já está cadastrado no sistema");
	}
}
