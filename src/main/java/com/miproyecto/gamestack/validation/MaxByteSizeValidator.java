package com.miproyecto.gamestack.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxByteSizeValidator implements ConstraintValidator<MaxByteSize, byte[]> {

    private int maxSize;

    @Override
    public void initialize(MaxByteSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        return value == null || value.length <= maxSize;
    }
}
