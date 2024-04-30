package com.tierlist.tierlist.category.adapter.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  @Override
  public boolean existsByName(String name) {
    return categoryJpaRepository.existsByName(name);
  }

  @Override
  public Category save(Category category) {
    return categoryJpaRepository.save(CategoryJpaEntity.from(category)).toCategory();
  }

  @Override
  public Optional<Category> findById(Long id) {
    return categoryJpaRepository.findById(id).map(CategoryJpaEntity::toCategory);
  }

  @Override
  public boolean existsById(Long categoryId) {
    return categoryJpaRepository.existsById(categoryId);
  }
}
