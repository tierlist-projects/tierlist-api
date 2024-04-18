package com.tierlist.tierlist.member.application.port.in.service;

import com.tierlist.tierlist.member.application.domain.model.command.EmailVerificationConfirmCommand;
import com.tierlist.tierlist.member.application.domain.model.command.SendEmailVerificationCommand;

public interface EmailVerificationUseCase {

  void sendVerificationEmail(SendEmailVerificationCommand command);

  boolean verifyEmail(EmailVerificationConfirmCommand command);
}
