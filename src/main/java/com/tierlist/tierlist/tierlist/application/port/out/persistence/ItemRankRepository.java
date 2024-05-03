package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import java.util.List;
import java.util.Optional;

public interface ItemRankRepository {

  void deleteAllByTierlistId(Long tierlistId);

  List<ItemRank> saveAll(List<ItemRank> itemRanks);

  Optional<ItemRank> findByItemIdAndTierlistId(Long itemId, Long tierlistId);
}
