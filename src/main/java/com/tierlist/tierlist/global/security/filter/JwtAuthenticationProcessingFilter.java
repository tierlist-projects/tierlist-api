package com.tierlist.tierlist.global.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tierlist.tierlist.global.jwt.property.JwtProperties;
import com.tierlist.tierlist.global.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

  private static final String ACCESS_TOKEN_HEADER_NAME = "Authorization";

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = extractAccessToken(request);

    if (!StringUtils.hasText(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      Authentication authentication = jwtService.resolveAccessToken(accessToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JWTVerificationException jwtVerificationException) {
      request.setAttribute("jwtVerificationException", jwtVerificationException);
    }

    filterChain.doFilter(request, response);
  }

  private String extractAccessToken(HttpServletRequest request) {
    String refreshTokenWithType = request.getHeader(ACCESS_TOKEN_HEADER_NAME);

    if (!StringUtils.hasText(refreshTokenWithType) ||
        !refreshTokenWithType.startsWith(jwtProperties.getTokenType() + " ")) {
      return null;
    }
    return refreshTokenWithType.substring(jwtProperties.getTokenType().length() + 1);
  }
}
