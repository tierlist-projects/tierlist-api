package com.tierlist.tierlist.member.application.domain.model;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Password {

  private final String encodedPassword;

  private Password(String encodedPassword) {
    this.encodedPassword = encodedPassword;
  }

  public static Password fromRawPassword(String rawPassword, PasswordEncoder passwordEncoder) {
    return new Password(passwordEncoder.encode(rawPassword));
  }

  public static Password fromEncodedPassword(String encodedPassword) {
    return new Password(encodedPassword);
  }

  public boolean matches(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, encodedPassword);
  }
}
