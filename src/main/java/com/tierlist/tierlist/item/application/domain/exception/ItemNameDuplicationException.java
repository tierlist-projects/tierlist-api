package com.tierlist.tierlist.item.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.DuplicationException;

public class ItemNameDuplicationException extends DuplicationException {

  public ItemNameDuplicationException() {
    super(ErrorCode.ITEM_NAME_DUPLICATION_ERROR);
  }
}
