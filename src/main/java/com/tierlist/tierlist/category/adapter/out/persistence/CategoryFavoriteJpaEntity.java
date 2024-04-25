package com.tierlist.tierlist.category.adapter.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.CategoryFavorite;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categoryFavorite")
@Entity
public class CategoryFavoriteJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private Long memberId;

  private Long categoryId;

  public static CategoryFavoriteJpaEntity from(CategoryFavorite categoryFavorite) {
    return CategoryFavoriteJpaEntity.builder()
        .id(categoryFavorite.getId())
        .categoryId(categoryFavorite.getCategoryId())
        .memberId(categoryFavorite.getMemberId())
        .build();
  }

  public CategoryFavorite toCategoryFavorite() {
    return CategoryFavorite.builder()
        .id(id)
        .categoryId(categoryId)
        .memberId(memberId)
        .build();
  }
}
