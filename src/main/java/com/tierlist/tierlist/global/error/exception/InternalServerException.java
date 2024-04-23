package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class InternalServerException extends BusinessException {

  public InternalServerException() {
    super(ErrorCode.INTERNAL_SERVER_ERROR);
  }
}
