package com.example.imageservice.handler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ImageServiceExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<List> processUnmergeException(final ConstraintViolationException ex) {

        List errorMessageList = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        List propertyPathList = ex.getConstraintViolations().stream()
                .map(pathName -> pathName.getPropertyPath().toString())
                .collect(Collectors.toList());
        
        if(propertyPathList.contains("getImage.typeName")){
            return new ResponseEntity<>(errorMessageList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(errorMessageList, HttpStatus.BAD_REQUEST);

    }

}