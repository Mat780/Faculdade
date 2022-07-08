public class LessExperienceException extends Exception {
	public LessExperienceException(){
		super("O piloto da aeronave deve possuir no mínimo 1 ano de experiência e ter mais experiência que o co-piloto ");
	}
}
