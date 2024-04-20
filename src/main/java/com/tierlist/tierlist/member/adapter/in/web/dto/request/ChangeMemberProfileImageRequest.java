package com.tierlist.tierlist.member.adapter.in.web.dto.request;

import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberProfileImageCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangeMemberProfileImageRequest {

  private String profileImageName;

  public ChangeMemberProfileImageCommand toCommand() {
    return ChangeMemberProfileImageCommand.builder()
        .profileImageName(profileImageName)
        .build();
  }
}
