public class SameAirportException extends Exception{
    public SameAirportException(){
        super("O mesmo aeroporto está sendo usado como saída e decolagem. Por favor, mude o aeroporto de saída e/ou de destino.");
    }
    
}
