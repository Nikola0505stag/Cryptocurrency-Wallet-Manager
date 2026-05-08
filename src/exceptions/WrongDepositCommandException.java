package exceptions;

public class WrongDepositCommandException extends RuntimeException{

    public WrongDepositCommandException(String message){
        super(message);
    }

    public WrongDepositCommandException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
