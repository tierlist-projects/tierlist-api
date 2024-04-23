package com.tierlist.tierlist.category.application.port.in.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class CategoryResponse {

  private Long id;
  private String name;
  private Boolean isFavorite;

}
