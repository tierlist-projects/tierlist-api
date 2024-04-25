package com.tierlist.tierlist.category.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryFavoriteJpaRepository extends
    JpaRepository<CategoryFavoriteJpaEntity, Long> {

  Optional<CategoryFavoriteJpaEntity> findByMemberIdAndCategoryId(Long memberId, Long categoryId);

}
