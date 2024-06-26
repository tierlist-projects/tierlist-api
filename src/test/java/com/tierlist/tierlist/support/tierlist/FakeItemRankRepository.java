package com.tierlist.tierlist.support.tierlist;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.ItemRankRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeItemRankRepository implements ItemRankRepository {

  private List<ItemRank> data = new ArrayList<>();
  private Long autoGeneratedId = 0L;

  @Override
  public List<ItemRank> saveAll(List<ItemRank> itemRanks) {
    return itemRanks.stream().map(this::save).toList();
  }

  @Override
  public Optional<ItemRank> findByItemIdAndTierlistId(Long itemId, Long tierlistId) {
    return data.stream()
        .filter(item -> item.getItemId().equals(itemId) &&
            item.getTierlistId().equals(tierlistId))
        .findFirst();
  }

  private ItemRank save(ItemRank itemRank) {

    Long id = itemRank.getId();
    if (id == null || id == 0) {
      ItemRank newItemRank = ItemRank.builder()
          .id(++autoGeneratedId)
          .rank(itemRank.getRank())
          .itemId(itemRank.getItemId())
          .tierlistId(itemRank.getTierlistId())
          .image(itemRank.getImage())
          .orderIdx(itemRank.getOrderIdx())
          .build();
      data.add(newItemRank);
      return newItemRank;
    }

    data.removeIf(item -> item.getId().equals(id));
    data.add(itemRank);
    return itemRank;
  }

  @Override
  public void deleteAllByTierlistId(Long tierlistId) {
    data.removeIf(item -> item.getTierlistId().equals(tierlistId));
  }

}
