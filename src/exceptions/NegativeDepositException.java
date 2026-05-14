package exceptions;

public class NegativeDepositException extends RuntimeException {
    public NegativeDepositException(String message) {
        super(message);
    }

    public NegativeDepositException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
