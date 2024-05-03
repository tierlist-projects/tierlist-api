package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TierlistLikeJpaRepository extends JpaRepository<TierlistLikeJpaEntity, Long> {

  Optional<TierlistLikeJpaEntity> findByMemberIdAndTierlistId(Long memberId, Long tierlistId);

}
