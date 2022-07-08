public class OnlyLetterException extends Exception {
	public OnlyLetterException(String campo){
		super("É permitido somente letras no campo " + campo);
	}
}