package com.tierlist.tierlist.member.application.port.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import jakarta.mail.MessagingException;

public interface EmailVerificationSender {

  void send(String email, EmailVerificationCode code) throws MessagingException;

}
