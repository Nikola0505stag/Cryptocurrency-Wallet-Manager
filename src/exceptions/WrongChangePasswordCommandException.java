package exceptions;

public class WrongChangePasswordCommandException extends RuntimeException {
    public WrongChangePasswordCommandException(String message) {
        super(message);
    }

    public WrongChangePasswordCommandException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
