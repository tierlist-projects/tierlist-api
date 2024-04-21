package com.tierlist.tierlist.category.application.domain.validation.validator;

import com.tierlist.tierlist.category.application.domain.validation.CategoryName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CategoryNameValidator implements ConstraintValidator<CategoryName, String> {

  private static final String REGEX = "^(?=.*[a-zA-Z0-9가-힣 ])[a-zA-Z0-9가-힣 ]{2,20}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }
}
