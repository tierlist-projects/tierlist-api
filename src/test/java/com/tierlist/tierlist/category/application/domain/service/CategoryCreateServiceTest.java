package com.tierlist.tierlist.category.application.domain.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNameDuplicationException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.command.CreateCategoryCommand;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CategoryCreateServiceTest {

  private CategoryRepository categoryRepository;
  private CategoryCreateService categoryCreateService;

  @BeforeEach
  public void init() {
    categoryRepository = new FakeCategoryRepository();
    categoryCreateService = new CategoryCreateService(categoryRepository);
  }

  @Test
  void 카테고리를_생성한다() {

    // given
    String name = "카테고리1";
    CreateCategoryCommand command = CreateCategoryCommand.builder()
        .name(name)
        .build();

    // when
    Long id = categoryCreateService.create(command);

    // then
    Optional<Category> result = categoryRepository.findById(id);
    assertThat(result).isPresent();

    Category category = result.get();
    assertThat(category.getId()).isEqualTo(id);
    assertThat(category.getName()).isEqualTo(name);

  }

  @Test
  void 중복된_이름의_카테고리를_생성할_수_없다() {

    // given
    CreateCategoryCommand command = CreateCategoryCommand.builder()
        .name("카테고리1")
        .build();
    categoryRepository.save(command.toCategory());

    // when
    // then
    assertThatThrownBy(() -> {
      categoryCreateService.create(command);
    }).isInstanceOf(CategoryNameDuplicationException.class);

  }

}