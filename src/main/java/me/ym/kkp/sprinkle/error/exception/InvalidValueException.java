package me.ym.kkp.sprinkle.error.exception;

//유효하지 않은 값 요청
public class InvalidValueException extends RuntimeException {

    public static Integer errorCode = 400;

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }

}
