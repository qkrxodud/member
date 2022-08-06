package able.member.exhandler.exception;

public class CMessageSendTimeOverException extends RuntimeException {
    public CMessageSendTimeOverException() {
        super();
    }

    public CMessageSendTimeOverException(String message) {
        super(message);
    }

    public CMessageSendTimeOverException(String message, Throwable cause) {
        super(message, cause);
    }
}
