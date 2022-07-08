
public class EqualPilotSelectedException extends RuntimeException {
	public EqualPilotSelectedException(){
		super("O piloto e o co-piloto são a mesma pessoa, por favor selecione pessoas diferentes");
	}
}
