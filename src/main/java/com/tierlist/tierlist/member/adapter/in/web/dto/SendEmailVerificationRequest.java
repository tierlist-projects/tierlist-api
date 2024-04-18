package com.tierlist.tierlist.member.adapter.in.web.dto;

import com.tierlist.tierlist.member.application.domain.model.command.SendEmailVerificationCommand;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendEmailVerificationRequest {

  @Email
  private String email;

  public SendEmailVerificationCommand toCommand() {
    return SendEmailVerificationCommand.builder()
        .email(email)
        .build();
  }
}
