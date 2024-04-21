package com.tierlist.tierlist.category.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

  public CategoryNotFoundException() {
    super(ErrorCode.CATEGORY_NOT_FOUND_ERROR);
  }
}
