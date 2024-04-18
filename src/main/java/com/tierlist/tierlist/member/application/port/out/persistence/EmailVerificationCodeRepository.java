package com.tierlist.tierlist.member.application.port.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import java.time.Duration;

public interface EmailVerificationCodeRepository {

  void save(String email, EmailVerificationCode code, Duration expireDuration);

  EmailVerificationCode get(String email);

  void remove(String email);

  boolean has(String email);

}
