package com.miproyecto.gamestack.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxByteSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxByteSize {
    String message() default "El archivo es demasiado grande";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value(); // en bytes
}
