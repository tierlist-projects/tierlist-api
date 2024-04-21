package com.tierlist.tierlist.category.application.port.in.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

  private Long id;
  private String name;
  private Boolean isFavorite;

}
