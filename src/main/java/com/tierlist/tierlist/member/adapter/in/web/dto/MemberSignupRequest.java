package com.tierlist.tierlist.member.adapter.in.web.dto;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.domain.model.command.MemberSignupCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSignupRequest {

  private String email;

  private String nickname;

  private String password;

  private String code;


  public MemberSignupCommand toCommand() {
    return MemberSignupCommand.builder()
        .email(email)
        .nickname(nickname)
        .rawPassword(password)
        .code(EmailVerificationCode.of(code))
        .build();
  }
}