package com.tierlist.tierlist.member.adapter.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.port.out.infrastructure.EmailVerificationCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class RandomEmailVerificationCodeGenerator implements EmailVerificationCodeGenerator {

  @Override
  public EmailVerificationCode generate() {
    int number = (int) (Math.random() * 1000000);
    String code = String.format("%06d", number);

    return EmailVerificationCode.of(code);
  }
}
