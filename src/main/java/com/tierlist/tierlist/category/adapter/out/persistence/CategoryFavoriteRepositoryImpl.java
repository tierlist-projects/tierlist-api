package com.tierlist.tierlist.category.adapter.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryFavoriteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryFavoriteRepositoryImpl implements CategoryFavoriteRepository {

  private final CategoryFavoriteJpaRepository categoryFavoriteJpaRepository;

  @Override
  public CategoryFavorite save(CategoryFavorite categoryFavorite) {
    return categoryFavoriteJpaRepository.save(CategoryFavoriteJpaEntity.from(categoryFavorite))
        .toCategoryFavorite();
  }

  @Override
  public Optional<CategoryFavorite> findByMemberIdAndCategoryId(Long memberId, Long categoryId) {
    return categoryFavoriteJpaRepository.findByMemberIdAndCategoryId(memberId, categoryId)
        .map(CategoryFavoriteJpaEntity::toCategoryFavorite);
  }

  @Override
  public void delete(CategoryFavorite categoryFavorite) {
    categoryFavoriteJpaRepository.delete(CategoryFavoriteJpaEntity.from(categoryFavorite));
  }

}
