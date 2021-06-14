package br.com.zupacademy.armando.propostamicroservice.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { PresentOrFutureLocalDateTimeValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface PresentOrFutureLocalDateTime {

    String pattern();

    String message() default "Precisa ser uma data valida atual ou futura.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}