package abley.member.exhandler.exception;

public class CPhoneLoginFailedException extends RuntimeException {

    public CPhoneLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CPhoneLoginFailedException(String message) {
        super(message);
    }

    public CPhoneLoginFailedException() {
        super();
    }
}
