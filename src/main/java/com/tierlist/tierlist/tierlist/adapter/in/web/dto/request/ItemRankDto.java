package com.tierlist.tierlist.tierlist.adapter.in.web.dto.request;

import com.tierlist.tierlist.tierlist.application.domain.model.command.ItemRankCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRankDto {

  private Long itemId;
  private String itemRankImage;


  public ItemRankCommand toCommand() {
    return ItemRankCommand.builder()
        .itemId(itemId)
        .itemRankImage(itemRankImage)
        .build();
  }
}
