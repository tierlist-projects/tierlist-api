package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class DuplicationException extends BusinessException {

  public DuplicationException() {
    super(ErrorCode.DUPLICATION_ERROR);
  }

  public DuplicationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
