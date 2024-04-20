package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

  public AuthenticationException() {
    super(ErrorCode.AUTHENTICATION_ERROR);
  }

  public AuthenticationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
