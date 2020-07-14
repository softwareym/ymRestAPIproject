package me.ym.kkp.sprinkle.error.exception;

//토큰에 해당하는 뿌리기데이터 없음
public class NotFoundException extends RuntimeException {

    public static Integer errorCode = 404;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

