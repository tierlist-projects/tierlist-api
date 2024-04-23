package com.tierlist.tierlist.tierlist.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.AuthorizationException;

public class TierlistAuthorizationException extends AuthorizationException {

  public TierlistAuthorizationException() {
    super(ErrorCode.TIERLIST_AUTHORIZATION_ERROR);
  }
}
