package com.tierlist.tierlist.image.application.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.InfraStructureErrorException;

public class ImageUploadException extends InfraStructureErrorException {

  public ImageUploadException() {
    super(ErrorCode.IMAGE_UPLOAD_ERROR);
  }
}
