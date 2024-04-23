package com.tierlist.tierlist.tierlist.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.NotFoundException;

public class TierlistNotFoundException extends NotFoundException {

  public TierlistNotFoundException() {
    super(ErrorCode.TIERLIST_NOT_FOUND_ERROR);
  }
}
