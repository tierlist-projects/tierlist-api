package com.tierlist.tierlist.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  BUSINESS_ERROR("B-001", "요청에 오류가 존재합니다."),

  DUPLICATION_ERROR("D-001", "요청의 내용이 중복되었습니다."),
  EMAIL_DUPLICATION_ERROR("D-004", "사용자 이메일이 중복되었습니다."),
  NICKNAME_DUPLICATION_ERROR("D-003", "사용자 닉네임이 중복되었습니다."),

  INFRASTRUCTURE_ERROR("I-001", "외부 서버에 이상이 있습니다."),
  MAIL_DELIVERY_ERROR("I-002", "메일 전송 서버에 이상이 있습니다."),

  INVALID_REQUEST_EXCEPTION("IR-001", "적절한 요청이 아닙니다."),
  INVALID_EMAIL_VERIFICATION_CODE_EXCEPTION("IR-002", "이메일 검증 코드를 확인할 수 없습니다."),
  INVALID_TYPE_VALUE("IR-003", "요청 내용의 타입이 일치하지 않습니다."),
  INVALID_INPUT_VALUE("IR-004", "요청 내용이 유효하지 않습니다."),

  AUTHENTICATION_ERROR("A-001", "인증에 실패하였습니다."),
  INVALID_USERNAME_OR_PASSWORD("A-002", "아이디 또는 비밀번호가 일치하지 않습니다."),
  TOKEN_EXPIRED("A-003", "토큰이 만료되었습니다."),
  INVALID_TOKEN("A-004", "토큰이 유효하지 않습니다."),
  UNEXPECTED_REFRESH_TOKEN("A-005", "에상치 못한 토큰입니다. 비정상적인 접속이 예상됩니다.");

  private final String errorCode;
  private final String message;

  ErrorCode(final String errorCode, final String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
