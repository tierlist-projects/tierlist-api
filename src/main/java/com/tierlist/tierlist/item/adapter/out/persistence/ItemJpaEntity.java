package com.tierlist.tierlist.item.adapter.out.persistence;

import com.tierlist.tierlist.item.application.domain.model.Item;
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
@Table(name = "item")
@Entity
public class ItemJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private String name;

  private Long categoryId;

  public static ItemJpaEntity from(Item item) {
    return ItemJpaEntity.builder()
        .id(item.getId())
        .name(item.getName())
        .categoryId(item.getCategoryId())
        .build();
  }

  public Item toItem() {
    return Item.builder()
        .id(id)
        .name(name)
        .categoryId(categoryId)
        .build();
  }

}
