package com.tierlist.tierlist.member.adapter.in.web.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

  private String email;
  private String nickname;
  private String profileImage;

}
