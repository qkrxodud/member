package able.member.exhandler.exception;

public class CEmailLoginFailedException extends RuntimeException{

    public CEmailLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CEmailLoginFailedException(String message) {
        super(message);
    }

    public CEmailLoginFailedException() {
        super();
    }
}
