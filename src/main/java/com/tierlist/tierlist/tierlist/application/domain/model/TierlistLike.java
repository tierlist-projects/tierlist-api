package com.tierlist.tierlist.tierlist.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistLike {

  private Long id;
  private Long memberId;
  private Long tierlistId;

}
