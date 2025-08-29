package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

  private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB

  // type

  // height

  // width

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

    if (file == null || file.isEmpty()) {

      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
      return false; // No file uploaded, so consider it valid
    }
    // file size
    if (file.getSize() > MAX_FILE_SIZE) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
      return false;
    }

    // resolution
    // try {
    // BufferedImage bf = ImageIO.read(file.getInputStream());
    // if(bf.get)

    // } catch (IOException e) {
    // e.printStackTrace();
    // }

    return true;

  }
}
