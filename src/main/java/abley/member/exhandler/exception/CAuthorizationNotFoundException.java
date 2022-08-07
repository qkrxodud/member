package abley.member.exhandler.exception;

public class CAuthorizationNotFoundException extends RuntimeException{

    public CAuthorizationNotFoundException() {
        super();
    }

    public CAuthorizationNotFoundException(String message) {
        super(message);
    }

    public CAuthorizationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
