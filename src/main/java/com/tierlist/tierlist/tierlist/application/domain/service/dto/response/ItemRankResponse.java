package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemRankResponse {

  private Long id;
  private String name;
  private String itemRankImage;

}
