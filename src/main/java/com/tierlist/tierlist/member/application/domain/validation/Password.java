package com.tierlist.tierlist.member.application.domain.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.tierlist.tierlist.member.application.domain.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

  String message() default "password는 8자 이상, 20자 이상이어야 하고,"
      + "영문 대문자, 소문자, 숫자, 특수문자 ! _ @ $ % ^ & + = 만 허용합니다.";

  Class<?>[] groups() default {};

  Class<String>[] payload() default {};

}
