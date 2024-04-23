package com.tierlist.tierlist.global.error;

import com.tierlist.tierlist.global.error.exception.AuthenticationException;
import com.tierlist.tierlist.global.error.exception.AuthorizationException;
import com.tierlist.tierlist.global.error.exception.BusinessException;
import com.tierlist.tierlist.global.error.exception.DuplicationException;
import com.tierlist.tierlist.global.error.exception.InfraStructureErrorException;
import com.tierlist.tierlist.global.error.exception.InternalServerException;
import com.tierlist.tierlist.global.error.exception.InvalidRequestException;
import com.tierlist.tierlist.global.error.exception.NotFoundException;
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

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(
      NotFoundException notFoundException) {
    ErrorCode errorCode = notFoundException.getErrorCode();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<ErrorResponse> handleAuthorizationException(
      AuthorizationException authorizationException) {
    ErrorCode errorCode = authorizationException.getErrorCode();
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerException(
      InternalServerException internalServerException) {
    ErrorCode errorCode = internalServerException.getErrorCode();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.from(errorCode));
  }
}
