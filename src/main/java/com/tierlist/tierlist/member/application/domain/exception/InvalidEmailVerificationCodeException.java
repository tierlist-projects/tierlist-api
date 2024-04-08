package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.InvalidRequestException;

public class InvalidEmailVerificationCodeException extends InvalidRequestException {

  public InvalidEmailVerificationCodeException() {
    super(ErrorCode.INVALID_EMAIL_VERIFICATION_CODE_EXCEPTION);
  }
}
