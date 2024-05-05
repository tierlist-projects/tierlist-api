package com.tierlist.tierlist.tierlist.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemRank {

  private Long id;

  private Rank rank;

  private Long tierlistId;

  private Long itemId;

  private String image;

  private int orderIdx;

}
