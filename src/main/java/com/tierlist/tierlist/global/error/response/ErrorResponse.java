package com.tierlist.tierlist.global.error.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tierlist.tierlist.global.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

  private String errorCode;
  private String message;
  private String[] reasons;

  public ErrorResponse(String errorCode, String message, String[] reasons) {
    this.errorCode = errorCode;
    this.message = message;
    this.reasons = reasons;
  }

  private ErrorResponse(final String errorCode, final String message) {
    this.errorCode = errorCode;
    this.message = message;
  }

  public static ErrorResponse from(final ErrorCode errorCode) {
    return new ErrorResponse(errorCode.getErrorCode(), errorCode.getMessage());
  }

  public static ErrorResponse from(final ErrorCode errorCode, final String[] reasons) {
    return new ErrorResponse(errorCode.getErrorCode(), errorCode.getMessage(), reasons);
  }
}