package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.DuplicationException;

public class NicknameDuplicationException extends DuplicationException {

  public NicknameDuplicationException() {
    super(ErrorCode.NICKNAME_DUPLICATION_ERROR);
  }
}
