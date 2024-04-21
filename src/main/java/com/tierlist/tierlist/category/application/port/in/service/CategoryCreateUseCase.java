package com.tierlist.tierlist.category.application.port.in.service;

import com.tierlist.tierlist.category.application.domain.model.command.CreateCategoryCommand;

public interface CategoryCreateUseCase {

  Long create(CreateCategoryCommand command);

}
