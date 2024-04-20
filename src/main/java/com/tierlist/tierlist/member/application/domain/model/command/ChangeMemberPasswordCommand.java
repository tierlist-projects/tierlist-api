package com.tierlist.tierlist.member.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ChangeMemberPasswordCommand {

  private String password;
  private String newPassword;

}
