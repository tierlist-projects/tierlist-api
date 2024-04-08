package com.tierlist.tierlist.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  BUSINESS_ERROR("B-001", "요청에 오류가 존재합니다."),

  DUPLICATION_ERROR("D-001", "요청의 내용이 중복되었습니다."),
  EMAIL_DUPLICATION_ERROR("D-004", "사용자 이메일이 중복되었습니다."),
  NICKNAME_DUPLICATION_ERROR("D-003", "사용자 닉네임이 중복되었습니다."),

  INFRASTRUCTURE_ERROR("I-001", "외부 서버에 이상이 있습니다."),
  MAIL_DELIVERY_ERROR("I-002", "메일 전송 서버에 이상이 있습니다.");

  private final String errorCode;
  private final String message;

  ErrorCode(final String errorCode, final String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
