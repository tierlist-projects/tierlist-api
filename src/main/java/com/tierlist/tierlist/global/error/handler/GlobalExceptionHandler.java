package com.tierlist.tierlist.global.error.handler;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
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
  public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(
      HandlerMethodValidationException e) {

    final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;

    String[] reasons = e.getAllErrors().stream().map(
        MessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

    return ResponseEntity.badRequest().body(ErrorResponse.from(errorCode, reasons));
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
    final ErrorCode errorCode = ErrorCode.NOT_FOUND_ERROR;
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
      MaxUploadSizeExceededException e) {
    final ErrorCode errorCode = ErrorCode.IMAGE_SIZE_EXCEEDED_ERROR;
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(
      MissingServletRequestPartException e) {
    final ErrorCode errorCode = ErrorCode.INVALID_REQUEST_VALUE;
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(errorCode));
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
    log.error(e.getMessage(), e);
    final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity.internalServerError().body(ErrorResponse.from(errorCode));
  }

}
