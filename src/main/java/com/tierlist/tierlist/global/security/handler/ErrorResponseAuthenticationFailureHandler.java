package com.tierlist.tierlist.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
public class ErrorResponseAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {

    ErrorCode code = ErrorCode.INVALID_USERNAME_OR_PASSWORD;
    ErrorResponse errorResponse = ErrorResponse.from(code);

    String body = objectMapper.writeValueAsString(errorResponse);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.getWriter().write(body);
  }
}
