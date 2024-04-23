package com.tierlist.tierlist.item.application.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.tierlist.tierlist.item.application.domain.validation.validator.ItemNameValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ItemNameValidator.class)
@Documented
public @interface ItemName {

  String message() default "아이템 이름은 2자 이상 10자 이하, 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
      + "특수문자, 자음, 모음을 포함할 수 없습니다.";

  Class<?>[] groups() default {};

  Class<String>[] payload() default {};
}
