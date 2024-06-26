package com.tierlist.tierlist.category.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CategoryFavorite {

  private Long id;
  private Long categoryId;
  private Long memberId;

}
