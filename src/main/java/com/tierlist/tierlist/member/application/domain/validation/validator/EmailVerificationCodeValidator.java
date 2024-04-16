package com.tierlist.tierlist.member.application.domain.validation.validator;

import com.tierlist.tierlist.member.application.domain.validation.EmailVerificationCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EmailVerificationCodeValidator implements
    ConstraintValidator<EmailVerificationCode, String> {

  private static final String REGEX = "^[0-9]{6}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }
}
