package me.ym.kkp.sprinkle.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import me.ym.kkp.sprinkle.error.exception.ExpiredException;
import me.ym.kkp.sprinkle.error.exception.ForbiddenException;
import me.ym.kkp.sprinkle.error.exception.InvalidValueException;
import me.ym.kkp.sprinkle.error.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ControllerAdvice : 모든 예외를 한 곳에서 처리할 수 있도록 핸들링
 * @ExceptionHandler : @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능 / @ExceptionHandler라는 어노테이션을 쓰고 인자로 캐치하고 싶은 예외클래스를 등록해주면 끝
 *
 */
@ControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public Error catchNotFoundData(HttpServletRequest req, NotFoundException ex){
        return new Error(req.getRequestURL().toString(), ex.getLocalizedMessage(), NotFoundException.errorCode);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public Error catchForbiddenData(HttpServletRequest req, ForbiddenException ex) {
        return new Error(req.getRequestURL().toString(), ex.getLocalizedMessage(), ForbiddenException.errorCode);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    @ResponseBody
    public Error catchInvalidData(HttpServletRequest req, InvalidValueException ex) {
        return new Error(req.getRequestURL().toString(), ex.getLocalizedMessage(), InvalidValueException.errorCode);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(ExpiredException.class)
    @ResponseBody
    public Error catchExpiredData(HttpServletRequest req, ExpiredException ex) {
        return new Error(req.getRequestURL().toString(), ex.getLocalizedMessage(), ExpiredException.errorCode);
    }

}
