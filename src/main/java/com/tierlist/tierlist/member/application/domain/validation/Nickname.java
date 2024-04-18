package com.tierlist.tierlist.member.application.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.tierlist.tierlist.member.application.domain.validation.validator.NicknameValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
@Documented
public @interface Nickname {

  String message() default "nickname은 2자 이상 10자 이하, 영어, 숫자 또는 한글로 구성되어야 하고,"
      + "공백, 특수문자, 자음, 모음을 포함할 수 없습니다.";

  Class<?>[] groups() default {};

  Class<String>[] payload() default {};

}
