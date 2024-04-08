package com.tierlist.tierlist.member.adapter.in.web.dto;

import com.tierlist.tierlist.member.application.domain.model.command.EmailVerificationConfirmCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailVerificationConfirmRequest {

  private String email;
  private String code;

  public EmailVerificationConfirmCommand toCommand() {
    return EmailVerificationConfirmCommand.builder()
        .email(email)
        .code(code)
        .build();
  }

}
