package com.scm.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.scm.validators.FileValidator;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
    ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
  // it is annotation
  String message() default "Invalid file";

  Class<?>[] groups() default {};

  Class<? extends jakarta.validation.Payload>[] payload() default {};

}
