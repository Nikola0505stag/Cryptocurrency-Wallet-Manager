package exceptions;

public class WrongAssetIDException extends RuntimeException {
    public WrongAssetIDException(String message) {
        super(message);
    }

    public WrongAssetIDException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
