package peaksoft.springsecuritybasicdemo.exceptions;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //404
    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException e) {
       return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(value = { UsernameNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleUserNotFoundException(NotFoundException e) {
        return ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }


    //400
    @ExceptionHandler(value = { BadRequestException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException e) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }

    //403
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleForbiddenException(ForbiddenException e) {
        return ExceptionResponse.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }

    // 500
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleServletException(ServletException e) {
        return ExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .className(e.getClass().getSimpleName())
                .build();
    }


}
