package br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler;

import org.springframework.http.HttpStatus;

public class ApiGenericException extends Throwable {

    private HttpStatus httpStatus;
    private String message;

    public ApiGenericException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
