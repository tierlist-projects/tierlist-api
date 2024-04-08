package com.tierlist.tierlist.member.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Member {

  private Long id;

  private String nickname;

  private Password password;

  private String email;

}
