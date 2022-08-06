package able.member.exhandler.exception;

public class CMessageSendCountOverException extends RuntimeException {
    public CMessageSendCountOverException() {
        super();
    }

    public CMessageSendCountOverException(String message) {
        super(message);
    }

    public CMessageSendCountOverException(String message, Throwable cause) {
        super(message, cause);
    }
}
