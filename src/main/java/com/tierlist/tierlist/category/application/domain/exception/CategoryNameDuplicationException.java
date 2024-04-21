package com.tierlist.tierlist.category.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.DuplicationException;

public class CategoryNameDuplicationException extends DuplicationException {

  public CategoryNameDuplicationException() {
    super(ErrorCode.CATEGORY_NAME_DUPLICATION_ERROR);
  }
}
