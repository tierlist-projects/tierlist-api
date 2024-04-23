package com.tierlist.tierlist.tierlist.application.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.tierlist.tierlist.tierlist.application.domain.validation.validator.TierlistTitleValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = TierlistTitleValidator.class)
@Documented
public @interface TierlistTitle {

  String message() default "티어리스트 제목은 2자 이상 25자 이하, 영어, 숫자 한글 또는 스페이스로 구성되어야 하고,"
      + "특수문자, 자음, 모음을 포함할 수 없습니다.";

  Class<?>[] groups() default {};

  Class<String>[] payload() default {};
}
