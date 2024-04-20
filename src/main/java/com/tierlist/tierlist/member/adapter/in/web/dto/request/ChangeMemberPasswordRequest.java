package com.tierlist.tierlist.member.adapter.in.web.dto.request;

import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberPasswordCommand;
import com.tierlist.tierlist.member.application.domain.validation.Password;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeMemberPasswordRequest {

  private String password;

  @Password
  private String newPassword;

  public ChangeMemberPasswordCommand toCommand() {
    return ChangeMemberPasswordCommand.builder()
        .password(password)
        .newPassword(newPassword)
        .build();
  }
}
