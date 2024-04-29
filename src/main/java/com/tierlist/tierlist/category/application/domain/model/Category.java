package com.tierlist.tierlist.category.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Category {

  private Long id;

  private String name;

  private int favoriteCount;

  public void addFavorite() {
    favoriteCount++;
  }

  public void removeFavorite() {
    favoriteCount--;
  }
}
