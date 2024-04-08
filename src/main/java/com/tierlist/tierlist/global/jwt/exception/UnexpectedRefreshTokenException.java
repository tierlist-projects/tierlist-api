package com.tierlist.tierlist.global.jwt.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class UnexpectedRefreshTokenException extends JWTVerificationException {

  public static final String ERROR_MESSAGE = "Connect From Another Location";

  public UnexpectedRefreshTokenException() {
    super(ERROR_MESSAGE);
  }
}