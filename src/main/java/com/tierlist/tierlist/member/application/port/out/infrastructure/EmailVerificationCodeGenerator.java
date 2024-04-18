package com.tierlist.tierlist.member.application.port.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;

public interface EmailVerificationCodeGenerator {

  EmailVerificationCode generate();

}
