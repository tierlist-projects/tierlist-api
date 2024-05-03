package com.tierlist.tierlist.support.tierlist;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLikeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeTierlistLikeRepository implements TierlistLikeRepository {

  private List<TierlistLike> data = new ArrayList<>();
  private Long autoGeneratedId = 0L;

  @Override
  public TierlistLike save(TierlistLike tierlistLike) {
    Long id = tierlistLike.getId();
    if (id == null || id == 0) {
      TierlistLike newTierlistLike = TierlistLike.builder()
          .id(++autoGeneratedId)
          .memberId(tierlistLike.getMemberId())
          .tierlistId(tierlistLike.getTierlistId())
          .build();
      data.add(newTierlistLike);
      return newTierlistLike;
    }

    data.removeIf(item -> item.getId().equals(id));
    data.add(tierlistLike);
    return tierlistLike;
  }

  @Override
  public Optional<TierlistLike> findByMemberIdAndTierlistId(Long memberId, Long tierlistId) {
    return data.stream()
        .filter(item -> item.getMemberId().equals(memberId) &&
            item.getTierlistId().equals(tierlistId))
        .findFirst();
  }

  @Override
  public void delete(TierlistLike tierlistLike) {
    data.removeIf(item -> item.getId().equals(tierlistLike.getId()));
  }
}