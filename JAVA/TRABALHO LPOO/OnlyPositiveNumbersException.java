public class OnlyPositiveNumbersException extends Exception {
	public OnlyPositiveNumbersException(String campo){
		super("É permitido apenas números positivos, acima de 0, no campo " + campo);
	}
}