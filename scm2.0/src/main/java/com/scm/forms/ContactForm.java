package com.scm.forms;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import com.scm.validators.ValidFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ContactForm {

  @NotBlank(message = "Name is required")
  private String name;

  @Email(message = "Email is required")
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Phone number is required")
  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
  private String phoneNumber;

  @NotBlank(message = "Address is required")
  private String address;

  private MultipartFile picture;

  private String descriptions;

  private String websiteLink;

  private String githubLink;

  // create annotation which validate our file.
  // size and resolution validate
  @ValidFile(message = "Invalid file. Must be image < 2MB")
  private MultipartFile contactImage;

}
