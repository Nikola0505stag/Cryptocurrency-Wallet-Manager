package exceptions;

public class ZeroDeposit extends RuntimeException {

    public ZeroDeposit(String message) {
        super(message);
    }

    public ZeroDeposit(String message, Throwable throwable) {
        super(message, throwable);
    }

}
