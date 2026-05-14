package exceptions;

public class WrongListOfferCommandException extends RuntimeException {

    public WrongListOfferCommandException(String message) {
        super(message);
    }

    public WrongListOfferCommandException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
