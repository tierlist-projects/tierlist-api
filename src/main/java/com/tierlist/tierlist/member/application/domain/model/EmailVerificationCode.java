package com.tierlist.tierlist.member.application.domain.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailVerificationCode {

  private final String code;

  public static EmailVerificationCode of(String code) {
    return new EmailVerificationCode(code);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    EmailVerificationCode that = (EmailVerificationCode) o;

    return Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return code != null ? code.hashCode() : 0;
  }
}
