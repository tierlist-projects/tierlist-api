package com.tierlist.tierlist.global.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.response.ErrorResponse;
import com.tierlist.tierlist.global.jwt.property.JwtProperties;
import com.tierlist.tierlist.global.jwt.service.JwtService;
import com.tierlist.tierlist.global.security.dto.response.TokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenReissueFilter extends OncePerRequestFilter {

  private static final String REFRESH_TOKEN_HEADER_NAME = "Refresh-Token";
  private static final AntPathRequestMatcher PATH_REQUEST_MATCHER
      = new AntPathRequestMatcher("/reissue", "POST");

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;
  private final ObjectMapper objectMapper;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (!PATH_REQUEST_MATCHER.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String refreshToken = extractRefreshToken(request);

    if (!StringUtils.hasText(refreshToken)) {
      throw new AuthenticationServiceException("Refresh Token required when reissue");
    }

    try {
      TokenResponse tokenResponse = jwtService.reissue(refreshToken);
      tokenResponse.send(response, objectMapper, HttpStatus.OK);
    } catch (JWTVerificationException jwtVerificationException) {
      request.setAttribute("jwtVerificationException", jwtVerificationException);
      authenticationEntryPoint.commence(request, response, null);
    }

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

  private String extractRefreshToken(HttpServletRequest request) {
    String refreshTokenWithType = request.getHeader(REFRESH_TOKEN_HEADER_NAME);
    if (!StringUtils.hasText(refreshTokenWithType) ||
        !refreshTokenWithType.startsWith(jwtProperties.getTokenType() + " ")) {
      return null;
    }
    return refreshTokenWithType.substring(jwtProperties.getTokenType().length() + 1);
  }

}
