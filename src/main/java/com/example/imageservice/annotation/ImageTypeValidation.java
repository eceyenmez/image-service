package com.example.imageservice.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageTypeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageTypeValidation {
    String message() default "Image Type not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
