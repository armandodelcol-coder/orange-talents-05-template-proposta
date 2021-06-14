package br.com.zupacademy.armando.propostamicroservice.core.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PresentOrFutureLocalDateTimeValidator implements ConstraintValidator<PresentOrFutureLocalDateTime, String> {

    private String pattern;

    @Override
    public void initialize(PresentOrFutureLocalDateTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        }

        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(value, datePattern);
            return !dateTime.isBefore(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

}
