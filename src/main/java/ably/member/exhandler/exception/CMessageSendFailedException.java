package ably.member.exhandler.exception;

public class CMessageSendFailedException extends RuntimeException {
    public CMessageSendFailedException() {
        super();
    }

    public CMessageSendFailedException(String message) {
        super(message);
    }

    public CMessageSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
