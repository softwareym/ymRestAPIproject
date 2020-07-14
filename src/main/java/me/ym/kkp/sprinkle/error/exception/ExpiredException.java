package me.ym.kkp.sprinkle.error.exception;

//토큰이나 보관시간이 만료됨
public class ExpiredException extends RuntimeException {

    public static Integer errorCode = 406;

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

}
