package br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponseBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<RequestFieldErrorDto> fieldErrorsDto = fieldErrors.stream().map(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            RequestFieldErrorDto requestFieldErrorDto = new RequestFieldErrorDto(fieldError.getField(), message);
            return requestFieldErrorDto;
        }).collect(Collectors.toList());
        CustomErrorResponseBody customErrorResponseBody = new CustomErrorResponseBody(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Alguns campos não foram preenchidos corretamente."
        );
        customErrorResponseBody.setDetails(Collections.singletonList(fieldErrorsDto));
        return ResponseEntity.badRequest().body(customErrorResponseBody);
    }

    @ExceptionHandler(ApiGenericException.class)
    public ResponseEntity<CustomErrorResponseBody> handleApiGenericException(ApiGenericException exception) {
        CustomErrorResponseBody customErrorResponseBody = new CustomErrorResponseBody(
                exception.getHttpStatus().getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(exception.getHttpStatus()).body(customErrorResponseBody);
    }

}