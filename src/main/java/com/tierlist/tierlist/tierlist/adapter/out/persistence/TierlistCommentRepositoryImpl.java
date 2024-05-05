package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistCommentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistCommentRepositoryImpl implements TierlistCommentRepository {

  private final TierlistCommentJpaRepository tierlistCommentJpaRepository;

  @Override
  public TierlistComment save(TierlistComment tierlistComment) {
    return tierlistCommentJpaRepository.save(TierlistCommentJpaEntity.from(tierlistComment))
        .toTierlistComment();
  }

  @Override
  public Optional<TierlistComment> findById(Long tierlistCommentId) {
    return tierlistCommentJpaRepository.findById(tierlistCommentId)
        .map(TierlistCommentJpaEntity::toTierlistComment);
  }
}
