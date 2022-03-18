package com.example.imageservice.annotation;

import com.example.imageservice.model.PredefinedImageType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageTypeValidator implements ConstraintValidator<ImageTypeValidation, String> {

    @Override
    public void initialize(ImageTypeValidation imageTypeValidation) {
    }

    @Override
    public boolean isValid(String ImageType, ConstraintValidatorContext cxt) {
        return PredefinedImageType.contains(ImageType);
    }

}
