package com.tierlist.tierlist.item.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ItemCreateCommand {

  private Long categoryId;
  private String name;

}
