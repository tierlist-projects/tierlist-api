package com.tierlist.tierlist.global.error;

import com.tierlist.tierlist.global.error.exception.AuthenticationException;
import com.tierlist.tierlist.global.error.exception.BusinessException;
import com.tierlist.tierlist.global.error.exception.DuplicationException;
import com.tierlist.tierlist.global.error.exception.InfraStructureErrorException;
import com.tierlist.tierlist.global.error.exception.InvalidRequestException;
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

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<ErrorResponse> handleDuplicationException(
      InvalidRequestException invalidRequestException) {
    ErrorCode errorCode = invalidRequestException.getErrorCode();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(InfraStructureErrorException.class)
  public ResponseEntity<ErrorResponse> handleInfraStructureException(
      InfraStructureErrorException infraStructureErrorException) {
    ErrorCode errorCode = infraStructureErrorException.getErrorCode();
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(
      AuthenticationException authenticationException) {
    ErrorCode errorCode = authenticationException.getErrorCode();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.from(errorCode));
  }

}
