package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.ItemRankRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ItemRankRepositoryImpl implements ItemRankRepository {

  private final ItemRankJpaRepository itemRankJpaRepository;

  @Override
  public void deleteAllByTierlistId(Long tierlistId) {
    itemRankJpaRepository.deleteAllByTierlistId(tierlistId);
  }

  @Override
  public List<ItemRank> saveAll(List<ItemRank> itemRanks) {
    return itemRankJpaRepository.saveAll(ItemRankJpaEntity.from(itemRanks)).stream()
        .map(item -> item.toItemRank()).toList();
  }

  @Override
  public Optional<ItemRank> findByItemIdAndTierlistId(Long itemId, Long tierlistId) {
    return itemRankJpaRepository.findByItemIdAndTierlistId(itemId, tierlistId);
  }

}
