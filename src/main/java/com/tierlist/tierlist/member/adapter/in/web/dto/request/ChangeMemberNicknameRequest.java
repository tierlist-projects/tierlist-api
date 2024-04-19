package com.tierlist.tierlist.member.adapter.in.web.dto.request;

import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberNicknameCommand;
import com.tierlist.tierlist.member.application.domain.validation.Nickname;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeMemberNicknameRequest {

  @Nickname
  private String nickname;

  public ChangeMemberNicknameCommand toCommand() {
    return ChangeMemberNicknameCommand.builder()
        .nickname(nickname)
        .build();
  }
}

