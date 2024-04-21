package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class NotFoundException extends BusinessException {

  public NotFoundException() {
    super(ErrorCode.NOT_FOUND_ERROR);
  }

  public NotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}
