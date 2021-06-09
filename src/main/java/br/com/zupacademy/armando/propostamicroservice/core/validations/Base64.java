package br.com.zupacademy.armando.propostamicroservice.core.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { Base64Validator.class })
@Target({ FIELD })
@Retention(RUNTIME)
public @interface Base64 {

    String message() default "Campo precisa ser enviado em fromato Base64 válido.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
