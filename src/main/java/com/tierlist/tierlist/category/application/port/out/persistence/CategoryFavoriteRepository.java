package com.tierlist.tierlist.category.application.port.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import java.util.Optional;

public interface CategoryFavoriteRepository {

  CategoryFavorite save(CategoryFavorite categoryFavorite);

  Optional<CategoryFavorite> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

  void delete(CategoryFavorite categoryFavorite);
}
