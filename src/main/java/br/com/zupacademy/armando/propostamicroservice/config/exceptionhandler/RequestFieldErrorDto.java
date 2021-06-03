package br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler;

public class RequestFieldErrorDto {

    private String field;
    private String error;

    public RequestFieldErrorDto(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}