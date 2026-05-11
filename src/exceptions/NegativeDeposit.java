package exceptions;

public class NegativeDeposit extends RuntimeException {

    public NegativeDeposit(String message) {
        super(message);
    }

    public NegativeDeposit(String message, Throwable throwable) {
        super(message, throwable);
    }
}
