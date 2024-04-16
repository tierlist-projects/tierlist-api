package com.tierlist.tierlist.member.application.domain.model.command;

import com.tierlist.tierlist.global.validation.Validator;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.domain.validation.EmailVerificationCode;
import com.tierlist.tierlist.member.application.domain.validation.Nickname;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Getter
public class MemberSignupCommand {

  @Email
  private String email;

  @Nickname
  private String nickname;

  @com.tierlist.tierlist.member.application.domain.validation.Password
  private String rawPassword;

  @EmailVerificationCode
  private String code;

  public MemberSignupCommand(String email, String nickname, String rawPassword,
      String code) {
    this.email = email;
    this.nickname = nickname;
    this.rawPassword = rawPassword;
    this.code = code;

    Validator.validate(this);
  }

  public Member toMember(PasswordEncoder passwordEncoder) {
    return Member.builder()
        .email(email)
        .nickname(nickname)
        .password(Password.fromRawPassword(rawPassword, passwordEncoder))
        .build();
  }
}
