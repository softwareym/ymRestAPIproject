package me.ym.kkp.sprinkle.error.exception;

//접근할 수 없음
public class ForbiddenException extends RuntimeException {

    public static Integer errorCode = 403;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

}
