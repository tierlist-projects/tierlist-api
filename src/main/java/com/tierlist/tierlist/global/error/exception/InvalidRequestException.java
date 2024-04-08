package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class InvalidRequestException extends BusinessException {

  public InvalidRequestException() {
    super(ErrorCode.INVALID_REQUEST_EXCEPTION);
  }

  public InvalidRequestException(ErrorCode errorCode) {
    super(errorCode);
  }
}
