package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.AuthenticationException;

public class InvalidPasswordException extends AuthenticationException {

  public InvalidPasswordException() {
    super(ErrorCode.INVALID_PASSWORD);
  }
}
