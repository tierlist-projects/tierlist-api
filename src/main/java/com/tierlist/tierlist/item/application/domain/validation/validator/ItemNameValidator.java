package com.tierlist.tierlist.item.application.domain.validation.validator;

import com.tierlist.tierlist.item.application.domain.validation.ItemName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ItemNameValidator implements ConstraintValidator<ItemName, String> {

  private static final String REGEX = "^(?=.*[a-zA-Z0-9가-힣 ])[a-zA-Z0-9가-힣 ]{2,10}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }

}
