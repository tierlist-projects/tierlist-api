package com.tierlist.tierlist.member.application.domain.model.command;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@AllArgsConstructor
@Getter
public class MemberSignupCommand {


  private String nickname;

  private String rawPassword;

  private String email;

  private EmailVerificationCode code;

  public Member toMember(PasswordEncoder passwordEncoder) {
    return Member.builder()
        .email(email)
        .nickname(nickname)
        .password(Password.fromRawPassword(rawPassword, passwordEncoder))
        .build();
  }
}
