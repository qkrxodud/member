package able.member.exhandler.exception;

public class CMessageCheckFailedException extends RuntimeException {
    public CMessageCheckFailedException() {
        super();
    }

    public CMessageCheckFailedException(String message) {
        super(message);
    }

    public CMessageCheckFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
