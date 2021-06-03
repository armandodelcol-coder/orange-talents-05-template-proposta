package br.com.zupacademy.armando.propostamicroservice.config.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomErrorResponseBody {

    private String type;
    private String message;
    private List<Object> details;

    public CustomErrorResponseBody(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getDetails() {
        return details;
    }

    public void setDetails(List<Object> details) {
        this.details = details;
    }

}
