package com.tierlist.tierlist.tierlist.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemRankCommand {

  private Long itemId;
  private String itemRankImage;

}
