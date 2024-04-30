package com.tierlist.tierlist.topic.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Topic {

  private Long id;

  private Long categoryId;

  private String name;

  private int favoriteCount;
}
