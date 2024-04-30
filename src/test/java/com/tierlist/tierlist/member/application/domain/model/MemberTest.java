package com.tierlist.tierlist.member.application.domain.model;

import com.tierlist.tierlist.member.application.domain.exception.InvalidPasswordException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberTest {

  private PasswordEncoder passwordEncoder;

  private Member member;

  @BeforeEach
  void init() {
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    member = Member.builder()
        .email("test@test.com")
        .nickname("test")
        .password(Password.fromRawPassword("originalPassword", passwordEncoder))
        .build();
  }

  @Test
  void 다른_비밀번호로_비밀번호를_변경할_수_없다() {
    Assertions.assertThatThrownBy(
        () -> {
          member.changePassword("differentPassword", "newPassword", passwordEncoder);
        }
    ).isInstanceOf(InvalidPasswordException.class);
  }

  @Test
  void 비밀번호를_변경할_수_있다() {
    member.changePassword("originalPassword", "newPassword", passwordEncoder);
    Password password = member.getPassword();

    Assertions.assertThat(password.matches("newPassword", passwordEncoder)).isTrue();
  }

}