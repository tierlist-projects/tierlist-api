package com.tierlist.tierlist.global.error;

import com.tierlist.tierlist.global.error.exception.BusinessException;
import com.tierlist.tierlist.global.error.exception.DuplicationException;
import com.tierlist.tierlist.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(
      BusinessException businessException) {

    ErrorCode errorCode = businessException.getErrorCode();
    return ResponseEntity.badRequest().body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(DuplicationException.class)
  public ResponseEntity<ErrorResponse> handleDuplicationException(
      DuplicationException duplicationException) {
    ErrorCode errorCode = duplicationException.getErrorCode();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.from(errorCode));
  }

}
