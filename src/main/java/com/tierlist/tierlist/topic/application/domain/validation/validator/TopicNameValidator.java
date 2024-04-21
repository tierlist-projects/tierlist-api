package com.tierlist.tierlist.topic.application.domain.validation.validator;

import com.tierlist.tierlist.topic.application.domain.validation.TopicName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TopicNameValidator implements ConstraintValidator<TopicName, String> {

  private static final String REGEX = "^(?=.*[a-zA-Z0-9가-힣 ])[a-zA-Z0-9가-힣 ]{2,20}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !Objects.isNull(value) && value.matches(REGEX);
  }
}
