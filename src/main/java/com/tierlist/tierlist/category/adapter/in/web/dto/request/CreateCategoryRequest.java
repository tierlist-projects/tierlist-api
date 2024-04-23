package com.tierlist.tierlist.category.adapter.in.web.dto.request;

import com.tierlist.tierlist.category.application.domain.model.command.CreateCategoryCommand;
import com.tierlist.tierlist.category.application.domain.validation.CategoryName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryRequest {

  @CategoryName
  private String name;

  public CreateCategoryCommand toCommand() {
    return CreateCategoryCommand.builder()
        .name(name)
        .build();
  }

}
