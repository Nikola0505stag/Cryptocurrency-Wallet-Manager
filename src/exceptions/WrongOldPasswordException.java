package exceptions;

public class WrongOldPasswordException extends RuntimeException{

    public WrongOldPasswordException(String message) {
        super(message);
    }

    public WrongOldPasswordException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
