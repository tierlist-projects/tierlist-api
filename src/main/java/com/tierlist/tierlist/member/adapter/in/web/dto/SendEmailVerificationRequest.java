package com.tierlist.tierlist.member.adapter.in.web.dto;

import com.tierlist.tierlist.member.application.domain.model.command.SendEmailVerificationCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendEmailVerificationRequest {

  private String email;

  public SendEmailVerificationCommand toCommand() {
    return SendEmailVerificationCommand.builder()
        .email(email)
        .build();
  }
}
