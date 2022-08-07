package ably.member.exhandler.exception;

public class CUserDuplicationException extends RuntimeException {
    public CUserDuplicationException() {
        super();
    }

    public CUserDuplicationException(String message) {
        super(message);
    }

    public CUserDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
