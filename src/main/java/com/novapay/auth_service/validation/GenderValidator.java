package com.novapay.auth_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenderValidator.class.getName());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null || value.isEmpty()) {
            LOGGER.warn("Gender value is null or empty");
            return false;
        }
        if (value.equalsIgnoreCase("Male") || value.equalsIgnoreCase("Female")) {
            return true;
        }
        return false;
    }
}
