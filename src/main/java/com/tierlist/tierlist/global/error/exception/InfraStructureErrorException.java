package com.tierlist.tierlist.global.error.exception;

import com.tierlist.tierlist.global.error.ErrorCode;

public class InfraStructureErrorException extends BusinessException {

  public InfraStructureErrorException() {
    super(ErrorCode.INFRASTRUCTURE_ERROR);
  }

  public InfraStructureErrorException(ErrorCode errorCode) {
    super(errorCode);
  }
}
