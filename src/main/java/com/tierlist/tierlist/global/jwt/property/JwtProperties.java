package com.tierlist.tierlist.global.jwt.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtProperties {

  @Value("${tierlist.jwt.properties.secret}")
  private String secret;
  @Value("${tierlist.jwt.properties.access-token-expiration-seconds}")
  private Long accessTokenExpirationSeconds;
  @Value("${tierlist.jwt.properties.refresh-token-expiration-seconds}")
  private Long refreshTokenExpirationSeconds;
  @Value("${tierlist.jwt.properties.token-type}")
  private String tokenType;

}