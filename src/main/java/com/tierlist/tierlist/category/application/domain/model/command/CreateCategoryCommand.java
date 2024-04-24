package com.tierlist.tierlist.category.application.domain.model.command;

import com.tierlist.tierlist.category.application.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateCategoryCommand {

  private String name;

  public Category toCategory() {
    return Category.builder()
        .name(name)
        .build();
  }
}
