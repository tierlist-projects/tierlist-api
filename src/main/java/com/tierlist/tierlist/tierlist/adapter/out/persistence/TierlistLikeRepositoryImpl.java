package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistLikeRepositoryImpl implements TierlistLikeRepository {

  private final TierlistLikeJpaRepository tierlistLikeJpaRepository;

  @Override
  public TierlistLike save(TierlistLike tierlistLike) {
    return tierlistLikeJpaRepository.save(TierlistLikeJpaEntity.from(tierlistLike))
        .toTierlistLike();
  }

  @Override
  public Optional<TierlistLike> findByMemberIdAndTierlistId(Long memberId, Long tierlistId) {
    return tierlistLikeJpaRepository.findByMemberIdAndTierlistId(memberId, tierlistId)
        .map(TierlistLikeJpaEntity::toTierlistLike);
  }

  @Override
  public void delete(TierlistLike tierlistLike) {
    tierlistLikeJpaRepository.delete(TierlistLikeJpaEntity.from(tierlistLike));
  }
}
