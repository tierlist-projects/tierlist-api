package com.tierlist.tierlist.member.adapter.in.web.dto.request;

import com.tierlist.tierlist.member.application.domain.model.command.EmailVerificationConfirmCommand;
import com.tierlist.tierlist.member.application.domain.validation.EmailVerificationCode;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailVerificationConfirmRequest {

  @Email
  private String email;

  @EmailVerificationCode
  private String code;

  public EmailVerificationConfirmCommand toCommand() {
    return EmailVerificationConfirmCommand.builder()
        .email(email)
        .code(code)
        .build();
  }

}
