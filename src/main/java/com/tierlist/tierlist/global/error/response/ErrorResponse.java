package com.tierlist.tierlist.global.error.response;

import com.tierlist.tierlist.global.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

  private String errorCode;
  private String message;

  private ErrorResponse(final String errorCode, final String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public static ErrorResponse from(final ErrorCode errorCode) {
    return new ErrorResponse(errorCode.getErrorCode(), errorCode.getMessage());
  }
}