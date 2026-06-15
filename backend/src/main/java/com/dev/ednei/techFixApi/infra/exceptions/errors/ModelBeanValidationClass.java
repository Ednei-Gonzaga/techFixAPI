package com.dev.ednei.techFixApi.infra.exceptions.errors;

import lombok.Getter;
import org.springframework.validation.FieldError;

public record ModelBeanValidationClass(
        String Field,
        String message
) {
    public ModelBeanValidationClass(FieldError fieldError){
        this(fieldError.getField(),  fieldError.getDefaultMessage());
    }
}
