package com.tierlist.tierlist.member.application.domain.model;

import com.tierlist.tierlist.member.application.domain.exception.InvalidPasswordException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
public class Member {

  private Long id;

  private String nickname;

  private Password password;

  private String email;

  private String profileImage;

  public void changeProfileImage(String profileImageName) {
    profileImage = profileImageName;
  }

  public void changePassword(String password, String newPassword, PasswordEncoder passwordEncoder) {
    if (!this.password.matches(password, passwordEncoder)) {
      throw new InvalidPasswordException();
    }
    this.password = Password.fromRawPassword(newPassword, passwordEncoder);
  }

  public void changeNickname(String newNickname) {
    this.nickname = newNickname;
  }
}
