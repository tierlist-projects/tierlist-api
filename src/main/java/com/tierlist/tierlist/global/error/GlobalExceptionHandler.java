package com.tierlist.tierlist.global.error;

import com.tierlist.tierlist.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

  /*
   * reqeustbody validation
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> exampleResponseValidation(
      MethodArgumentNotValidException e) {

    final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;

    String[] reasons = e.getAllErrors().stream().map(
        c -> (((FieldError) c).getField() + ": " + c.getDefaultMessage())).toArray(String[]::new);

    return ResponseEntity.badRequest().body(ErrorResponse.from(errorCode, reasons));
  }

  /*
   * pathvariable validation
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse> exampleResponseValidation(
      HandlerMethodValidationException e) {

    final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;

    String[] reasons = e.getAllErrors().stream().map(
        MessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

    return ResponseEntity.badRequest().body(ErrorResponse.from(errorCode, reasons));
  }

}
