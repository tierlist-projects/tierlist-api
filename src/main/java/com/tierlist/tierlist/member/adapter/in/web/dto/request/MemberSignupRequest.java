package com.tierlist.tierlist.member.adapter.in.web.dto.request;

import com.tierlist.tierlist.member.application.domain.model.command.MemberSignupCommand;
import com.tierlist.tierlist.member.application.domain.validation.EmailVerificationCode;
import com.tierlist.tierlist.member.application.domain.validation.Nickname;
import com.tierlist.tierlist.member.application.domain.validation.Password;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequest {

  @Email
  private String email;

  @Nickname
  private String nickname;

  @Password
  private String password;

  @EmailVerificationCode
  private String code;


  public MemberSignupCommand toCommand() {
    return MemberSignupCommand.builder()
        .email(email)
        .nickname(nickname)
        .rawPassword(password)
        .code(com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode.of(code))
        .build();
  }
}
