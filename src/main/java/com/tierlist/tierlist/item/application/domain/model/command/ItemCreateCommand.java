package com.tierlist.tierlist.item.application.domain.model.command;

import com.tierlist.tierlist.item.application.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ItemCreateCommand {

  private String name;
  private Long categoryId;

  public Item toItem() {
    return Item.builder()
        .name(name)
        .categoryId(categoryId)
        .build();
  }
}
