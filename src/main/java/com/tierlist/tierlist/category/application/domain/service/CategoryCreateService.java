package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNameDuplicationException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.command.CreateCategoryCommand;
import com.tierlist.tierlist.category.application.port.in.service.CategoryCreateUseCase;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryCreateService implements CategoryCreateUseCase {

  private final CategoryRepository categoryRepository;

  @Transactional
  @Override
  public Long create(CreateCategoryCommand command) {
    if (categoryRepository.existsByName(command.getName())) {
      throw new CategoryNameDuplicationException();
    }

    Category category = categoryRepository.save(command.toCategory());

    return category.getId();
  }
}
