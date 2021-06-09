package br.com.zupacademy.armando.propostamicroservice.core.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64, String> {

    private final Logger logger = LoggerFactory.getLogger(Base64Validator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        // Fiz um try/catch pois o validador isBase64 da biblioteca codec retorna muitos falsos positivos
        try {
            byte[] decode = java.util.Base64.getDecoder().decode(value.getBytes());
            new String(decode);
            return true;
        } catch (Exception e) {
            logger.warn("Formato Base64 inválido para o decodificador");
            return false;
        }
    }

}
