package com.tierlist.tierlist.tierlist.application.domain.validation.validator;

import com.tierlist.tierlist.tierlist.application.domain.validation.TierlistTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TierlistTitleValidator implements ConstraintValidator<TierlistTitle, String> {

  private static final String REGEX = "^(?=.*[a-zA-Z0-9가-힣 ])[a-zA-Z0-9가-힣 ]{2,25}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }
}
