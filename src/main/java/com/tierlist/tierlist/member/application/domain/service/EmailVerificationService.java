package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MailDeliveryException;
import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.domain.model.command.EmailVerificationConfirmCommand;
import com.tierlist.tierlist.member.application.domain.model.command.SendEmailVerificationCommand;
import com.tierlist.tierlist.member.application.port.in.service.EmailVerificationUseCase;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import com.tierlist.tierlist.member.application.port.out.infrastructure.EmailVerificationCodeGenerator;
import com.tierlist.tierlist.member.application.port.out.infrastructure.EmailVerificationSender;
import com.tierlist.tierlist.member.application.port.out.persistence.EmailVerificationCodeRepository;
import com.tierlist.tierlist.member.application.port.out.persistence.VerifiedEmailRepository;
import jakarta.mail.MessagingException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmailVerificationService implements EmailVerificationUseCase {

  private final MemberValidationUseCase memberValidationUseCase;

  private final EmailVerificationCodeRepository emailVerificationCodeRepository;
  private final VerifiedEmailRepository verifiedEmailRepository;

  private final EmailVerificationCodeGenerator codeGenerator;
  private final EmailVerificationSender mailSender;

  @Value("${tierlist.email.verification.expire-minute}")
  private int emailVerificationExpireMinute;

  @Value("${tierlist.email.verified.expire-minute}")
  private int verifiedEmailExpireMinute;

  @Transactional
  @Override
  public void sendVerificationEmail(SendEmailVerificationCommand command) {
    String email = command.getEmail();
    memberValidationUseCase.validateEmailDuplication(email);

    EmailVerificationCode code = codeGenerator.generate();
    emailVerificationCodeRepository.save(email, code,
        Duration.ofMinutes(emailVerificationExpireMinute));

    try {
      mailSender.send(command.getEmail(), code);
    } catch (MessagingException e) {
      emailVerificationCodeRepository.remove(email);
      throw new MailDeliveryException();
    }
  }

  @Transactional
  @Override
  public boolean verifyEmail(EmailVerificationConfirmCommand command) {
    String email = command.getEmail();
    EmailVerificationCode code = EmailVerificationCode.of(command.getCode());

    memberValidationUseCase.validateEmailDuplication(email);

    if (isVerify(email, code)) {
      verifiedEmailRepository.save(email, code, Duration.ofMinutes(verifiedEmailExpireMinute));
      emailVerificationCodeRepository.remove(email);
      return true;
    }

    return false;
  }

  private boolean isVerify(String email, EmailVerificationCode code) {
    return emailVerificationCodeRepository.has(email) &&
        emailVerificationCodeRepository.get(email).equals(code);
  }
}
