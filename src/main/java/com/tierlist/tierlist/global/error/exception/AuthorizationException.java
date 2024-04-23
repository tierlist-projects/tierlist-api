package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class AuthorizationException extends BusinessException {

  public AuthorizationException() {
    super(ErrorCode.AUTHORIZATION_ERROR);
  }

  public AuthorizationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
