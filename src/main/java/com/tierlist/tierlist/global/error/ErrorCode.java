package com.tierlist.tierlist.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

  INTERNAL_SERVER_ERROR("S-001", "Internal Server Error"),

  BUSINESS_ERROR("B-001", "요청에 오류가 존재합니다."),

  DUPLICATION_ERROR("D-001", "요청의 내용이 중복되었습니다."),
  EMAIL_DUPLICATION_ERROR("D-004", "사용자 이메일이 중복되었습니다."),
  NICKNAME_DUPLICATION_ERROR("D-003", "사용자 닉네임이 중복되었습니다."),
  CATEGORY_NAME_DUPLICATION_ERROR("D-004", "카테고리 이름이 중복되었습니다."),
  TOPIC_NAME_DUPLICATION_ERROR("D-005", "카테고리 내에서 토픽 이름이 중복되었습니다."),
  ITEM_NAME_DUPLICATION_ERROR("D-006", "카테고리 내에서 아이템 이름이 중복되었습니다."),

  INFRASTRUCTURE_ERROR("I-001", "외부 서버에 이상이 있습니다."),
  MAIL_DELIVERY_ERROR("I-002", "메일 전송 서버에 이상이 있습니다."),
  IMAGE_UPLOAD_ERROR("I-002", "이미지 업로드에 이상이 있습니다."),

  IMAGE_SIZE_EXCEEDED_ERROR("IM-002", "이미지 업로드 용량이 초과되었습니다."),

  INVALID_REQUEST_EXCEPTION("IR-001", "적절한 요청이 아닙니다."),
  INVALID_EMAIL_VERIFICATION_CODE_EXCEPTION("IR-002", "이메일 검증 코드를 확인할 수 없습니다."),
  INVALID_TYPE_VALUE("IR-003", "요청 내용의 타입이 일치하지 않습니다."),
  INVALID_REQUEST_VALUE("IR-004", "요청 내용이 유효하지 않습니다."),

  AUTHENTICATION_ERROR("A-001", "인증에 실패하였습니다."),
  INVALID_USERNAME_OR_PASSWORD("A-002", "아이디 또는 비밀번호가 일치하지 않습니다."),
  TOKEN_EXPIRED("A-003", "토큰이 만료되었습니다."),
  INVALID_TOKEN("A-004", "토큰이 유효하지 않습니다."),
  UNEXPECTED_REFRESH_TOKEN("A-005", "에상치 못한 토큰입니다. 비정상적인 접속이 예상됩니다."),
  INVALID_PASSWORD("A-006", "비밀번호가 일치하지 않습니다."),

  AUTHORIZATION_ERROR("AU-001", "권한이 존재하지 않습니다."),
  TIERLIST_AUTHORIZATION_ERROR("AU-002", "해당 티어리스트에 대한 권한이 없습니다."),

  NOT_FOUND_ERROR("NF-001", "요청한 리소스를 찾을 수 없습니다."),
  CATEGORY_NOT_FOUND_ERROR("NF-002", "해당 카테고리를 찾을 수 없습니다."),
  TOPIC_NOT_FOUND_ERROR("NF-002", "해당 토픽을 찾을 수 없습니다."),
  TIERLIST_NOT_FOUND_ERROR("NF-003", "해당 티어리스트를 찾을 수 없습니다."),
  TIERLIST_COMMENT_NOT_FOUND_ERROR("NF-004", "해당 티어리스트 댓글을 찾을 수 없습니다.");

  private final String code;
  private final String message;

  ErrorCode(final String code, final String message) {
    this.code = code;
    this.message = message;
  }
}
