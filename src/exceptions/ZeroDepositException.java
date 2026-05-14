package exceptions;

public class ZeroDepositException extends RuntimeException {
    public ZeroDepositException(String message) {
        super(message);
    }

    public ZeroDepositException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
