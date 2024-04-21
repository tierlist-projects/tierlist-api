package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.model.command.CreateCategoryCommand;
import com.tierlist.tierlist.category.application.port.in.service.CategoryCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class CategoryCreateService implements CategoryCreateUseCase {

  @Override
  public Long create(CreateCategoryCommand command) {
    return 0L;
  }
}
