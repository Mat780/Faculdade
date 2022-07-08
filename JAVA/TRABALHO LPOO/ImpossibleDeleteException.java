
public class ImpossibleDeleteException extends RuntimeException {
    public ImpossibleDeleteException(String type, String message) {
        super("É impossivel deletar o(a) " + type + " pois está vinculado(a) " + message);
    }
}
