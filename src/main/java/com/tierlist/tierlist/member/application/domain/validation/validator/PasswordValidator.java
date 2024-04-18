package com.tierlist.tierlist.member.application.domain.validation.validator;

import com.tierlist.tierlist.member.application.domain.validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  private static final String REGEX = "^[A-Za-z0-9!_@$%^&+=]{8,20}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }
}
