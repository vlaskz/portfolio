package io.vlaskz.portfolio.handler;

import io.vlaskz.portfolio.exception.BadRequestException;
import io.vlaskz.portfolio.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException)
    {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("BadRequestException - Check the Docs.")
                .details(badRequestException.getMessage())
                .build(), HttpStatus.BAD_REQUEST);

    }
}
