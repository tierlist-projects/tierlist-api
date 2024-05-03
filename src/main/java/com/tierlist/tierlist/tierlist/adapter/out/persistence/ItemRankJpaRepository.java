package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItemRankJpaRepository extends JpaRepository<ItemRankJpaEntity, Long> {

  @Modifying
  @Query("delete from ItemRankJpaEntity i where i.tierlistId = :tierlistId")
  void deleteAllByTierlistId(Long tierlistId);

  Optional<ItemRank> findByItemIdAndTierlistId(Long itemId, Long tierlistId);
}
