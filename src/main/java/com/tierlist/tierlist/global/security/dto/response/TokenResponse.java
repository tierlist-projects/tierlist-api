package com.tierlist.tierlist.global.security.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TokenResponse {

  private String tokenType;
  private String accessToken;
  private Long accessTokenExpiresIn;
  private String refreshToken;
  private Long refreshTokenExpiresIn;

  public void send(HttpServletResponse response, ObjectMapper objectMapper, HttpStatus status)
      throws IOException {
    String body = objectMapper.writeValueAsString(this);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.getWriter().write(body);
    response.setStatus(status.value());
  }
}