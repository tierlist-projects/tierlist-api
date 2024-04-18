package com.tierlist.tierlist.member.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.InfraStructureErrorException;

public class MailDeliveryException extends InfraStructureErrorException {

  public MailDeliveryException() {
    super(ErrorCode.MAIL_DELIVERY_ERROR);
  }
}
