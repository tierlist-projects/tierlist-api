package com.tierlist.tierlist.item.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Item {

  private Long id;
  private String name;
  private Long categoryId;
}
