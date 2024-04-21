package com.tierlist.tierlist.category.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CreateCategoryCommand {

  private String name;
}
