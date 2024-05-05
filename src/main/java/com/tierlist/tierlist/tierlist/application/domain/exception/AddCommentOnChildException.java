package com.tierlist.tierlist.tierlist.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.InvalidRequestException;

public class AddCommentOnChildException extends InvalidRequestException {

  public AddCommentOnChildException() {
    super(ErrorCode.CANNOT_ADD_COMMENT_AT_CHILD_COMMENT);
  }
}
