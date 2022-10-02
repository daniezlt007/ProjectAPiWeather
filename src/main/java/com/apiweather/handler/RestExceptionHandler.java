package com.apiweather.handler;

import com.apiweather.exception.ExceptionDatabase;
import com.apiweather.exception.NegocioException;
import com.apiweather.exception.ResourceExceptionDetails;
import com.apiweather.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Recurso não encontrado.")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleResourceNegocioException(NegocioException resourceNotFoundException){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Erro de negócio.")
                .detail(resourceNotFoundException.getMessage())
                .developerMessage(resourceNotFoundException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceptionDatabase.class)
    public ResponseEntity<?> handleDatabaseException(ExceptionDatabase exceptionDatabase){
        ResourceExceptionDetails rfnDetails = ResourceExceptionDetails
                .Builder
                .aResourceNotFoundDetails()
                .timestamp(new Date().getTime())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title("Erro no banco de dados.")
                .detail(exceptionDatabase.getMessage())
                .developerMessage(exceptionDatabase.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
