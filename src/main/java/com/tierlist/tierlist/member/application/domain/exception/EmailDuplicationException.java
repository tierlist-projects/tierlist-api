package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.DuplicationException;

public class EmailDuplicationException extends DuplicationException {

  public EmailDuplicationException() {
    super(ErrorCode.EMAIL_DUPLICATION_ERROR);
  }
}
