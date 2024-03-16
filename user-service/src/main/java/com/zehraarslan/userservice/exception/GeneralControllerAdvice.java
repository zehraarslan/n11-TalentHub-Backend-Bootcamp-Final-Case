package com.zehraarslan.userservice.exception;

import com.zehraarslan.userservice.general.RestResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
@Slf4j
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllException(Exception e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleSystemException(SystemException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllException(TransactionSystemException  e, WebRequest request) {
        String message = e.getCause() != null ? e.getCause().getMessage() : "An error occurred while processing the transaction";
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        String message = e.getCause() != null ? e.getConstraintViolations().iterator().next().getMessage() : "An error occurred while processing the transaction";
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
