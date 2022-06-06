import java.util.Scanner;

public class Leitor {
	Scanner leitor;

	public Leitor(){
		leitor = new Scanner(System.in);
	}

	public String leString(){
		return leitor.nextLine();
	}

	public int leInteger(){
		int numero = leitor.nextInt();
		leitor.nextLine();
		return numero;
	}

	public boolean leBoolean(){
		return leitor.nextBoolean();
	}

	public double leDouble(){
		return leitor.nextDouble();
	}

}
