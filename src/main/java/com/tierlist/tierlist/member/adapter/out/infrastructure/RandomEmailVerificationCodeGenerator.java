package com.tierlist.tierlist.member.adapter.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import com.tierlist.tierlist.member.application.port.out.infrastructure.EmailVerificationCodeGenerator;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomEmailVerificationCodeGenerator implements EmailVerificationCodeGenerator {

  private static final int MAX_VALUE = 999_999;
  private static Random random = new Random();

  @Override
  public EmailVerificationCode generate() {
    int number = random.nextInt(MAX_VALUE + 1);
    String code = String.format("%06d", number);

    return EmailVerificationCode.of(code);
  }
}
