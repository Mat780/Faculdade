public class InsufficientTaskTypeException extends Exception {
	public InsufficientTaskTypeException(){
		super("Na equipe de bordo é necessário que haja um comissário(a) para cada uma das seguintes funções:\n 1: Limpeza,\n 2: Serviço,\n 3: Instruções,\n 4: Recepção");
	}
}
