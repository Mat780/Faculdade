public class AttachedObjectException extends RuntimeException {
	public AttachedObjectException(String type, String message){
		super("Para poder atualizar " + type + " é necessário retira-lá de " + message);
	}
}
