package com.tierlist.tierlist.global.error;

import com.tierlist.tierlist.global.error.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * JSON Request Body 에서 type mismatch 가 발생할 때
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException httpMessageNotReadableException) {
    ErrorCode errorCode = ErrorCode.INVALID_TYPE_VALUE;
    final ErrorResponse response = ErrorResponse.from(errorCode);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ErrorResponse> handleConstraintViolationException(
      final ConstraintViolationException e) {

    final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;

    String[] reasons = e.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessage)
        .toArray(String[]::new);

    return ResponseEntity.badRequest().body(ErrorResponse.from(errorCode, reasons));
  }

}
