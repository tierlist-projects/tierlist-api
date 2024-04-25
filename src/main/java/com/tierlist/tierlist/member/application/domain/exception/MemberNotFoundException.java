package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

  public MemberNotFoundException() {
    super(ErrorCode.MEMBER_NOT_FOUND_ERROR);
  }
}
