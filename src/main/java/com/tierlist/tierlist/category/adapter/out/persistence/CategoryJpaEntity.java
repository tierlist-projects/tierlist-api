package com.tierlist.tierlist.category.adapter.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.Category;
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
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
@Entity
public class CategoryJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private String name;

  @ColumnDefault("0")
  private int favoriteCount;

  public static CategoryJpaEntity from(Category category) {
    return CategoryJpaEntity.builder()
        .id(category.getId())
        .name(category.getName())
        .favoriteCount(category.getFavoriteCount())
        .build();
  }

  public Category toCategory() {
    return Category.builder()
        .id(id)
        .name(name)
        .build();
  }
}
