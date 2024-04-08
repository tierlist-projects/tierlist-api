package com.tierlist.tierlist.member.application.domain.event;

import com.tierlist.tierlist.member.application.port.out.infrastructure.EmailVerificationSender;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailVerificationSendEventHandler {

  private final EmailVerificationSender emailVerificationSender;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void sendEmailVerification(EmailVerificationSendEvent event) {
    try {
      emailVerificationSender.send(event.getEmail(), event.getCode());
    } catch (MailException | MessagingException e) {
      log.error("이메일 인증 메일이 전송되지 않았습니다.", e);
    }
  }

}
