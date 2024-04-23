package com.tierlist.tierlist.item.adapter.in.web.dto.request;

import com.tierlist.tierlist.item.application.domain.model.command.ItemCreateCommand;
import com.tierlist.tierlist.item.application.domain.validation.ItemName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ItemCreateRequest {

  private Long categoryId;

  @ItemName
  private String name;

  public ItemCreateCommand toCommand() {
    return ItemCreateCommand.builder()
        .categoryId(categoryId)
        .name(name)
        .build();
  }

}
