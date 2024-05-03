package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import com.tierlist.tierlist.tierlist.application.domain.model.Rank;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "itemRank")
@Entity
public class ItemRankJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Rank rank;

  private Long tierlistId;

  private Long itemId;

  private String image;

  private int order;

  public static List<ItemRankJpaEntity> from(List<ItemRank> itemRanks) {
    return itemRanks.stream().map(ItemRankJpaEntity::from).toList();
  }

  public static ItemRankJpaEntity from(ItemRank itemRank) {
    return ItemRankJpaEntity.builder()
        .id(itemRank.getId())
        .rank(itemRank.getRank())
        .tierlistId(itemRank.getTierlistId())
        .itemId(itemRank.getItemId())
        .image(itemRank.getImage())
        .order(itemRank.getOrder())
        .build();
  }

  public ItemRank toItemRank() {
    return ItemRank.builder()
        .id(id)
        .rank(rank)
        .tierlistId(tierlistId)
        .itemId(itemId)
        .image(image)
        .order(order)
        .build();
  }
}
