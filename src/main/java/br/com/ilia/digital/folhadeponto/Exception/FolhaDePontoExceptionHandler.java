package br.com.ilia.digital.folhadeponto.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FolhaDePontoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleException(DomainException exception) {

        MessageExceptionResponse errorMessage = MessageExceptionResponse.builder()
                .mensagem(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorMessage, exception.getStatus());
    }

}
