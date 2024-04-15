package com.tierlist.tierlist.global.jwt.entrypoint;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.response.ErrorResponse;
import com.tierlist.tierlist.global.jwt.exception.UnexpectedRefreshTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;


  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    JWTVerificationException jwtVerificationException =
        (JWTVerificationException) request.getAttribute("jwtVerificationException");

    if (jwtVerificationException instanceof TokenExpiredException) {
      sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED, HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (jwtVerificationException instanceof UnexpectedRefreshTokenException) {
      sendErrorResponse(response, ErrorCode.UNEXPECTED_REFRESH_TOKEN,
          HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    if (jwtVerificationException != null) {
      sendErrorResponse(response, ErrorCode.INVALID_TOKEN, HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    sendErrorResponse(response, ErrorCode.AUTHENTICATION_ERROR,
        HttpServletResponse.SC_UNAUTHORIZED);
  }

  private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, int status)
      throws IOException {
    ErrorResponse errorResponse = ErrorResponse.from(errorCode);

    String body = objectMapper.writeValueAsString(errorResponse);
    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.getWriter().write(body);
  }
}
