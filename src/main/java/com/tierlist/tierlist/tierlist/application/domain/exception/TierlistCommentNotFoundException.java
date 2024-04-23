package com.tierlist.tierlist.tierlist.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.NotFoundException;

public class TierlistCommentNotFoundException extends NotFoundException {

  public TierlistCommentNotFoundException() {
    super(ErrorCode.TIERLIST_COMMENT_NOT_FOUND_ERROR);
  }
}
