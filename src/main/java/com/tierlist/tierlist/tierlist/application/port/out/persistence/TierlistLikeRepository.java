package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import java.util.Optional;

public interface TierlistLikeRepository {

  TierlistLike save(TierlistLike tierlistLike);

  Optional<TierlistLike> findByMemberIdAndTierlistId(Long memberId, Long tierlistId);

  void delete(TierlistLike tierlistLike);
}
