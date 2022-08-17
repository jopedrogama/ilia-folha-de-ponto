package br.com.ilia.digital.folhadeponto.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                        MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status,
                        WebRequest request) {

                String errorString = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

                MessageExceptionResponse errorMessage = MessageExceptionResponse.builder()
                                .mensagem(errorString)
                                .build();

                return new ResponseEntity<>(errorMessage,
                                HttpStatus.BAD_REQUEST);
        }

}
