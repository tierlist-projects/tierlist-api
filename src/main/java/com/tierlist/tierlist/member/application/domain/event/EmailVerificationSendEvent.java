package com.tierlist.tierlist.member.application.domain.event;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import lombok.Getter;

@Getter
public class EmailVerificationSendEvent {

  private String email;
  private EmailVerificationCode code;

  private EmailVerificationSendEvent(String email, EmailVerificationCode code) {
    this.email = email;
    this.code = code;
  }

  public static EmailVerificationSendEvent of(String email, EmailVerificationCode code) {
    return new EmailVerificationSendEvent(email, code);
  }
}
