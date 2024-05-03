package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistRepositoryImpl implements TierlistRepository {

  private final TierlistJpaRepository tierlistJpaRepository;

  @Override
  public Tierlist save(Tierlist tierlist) {
    return tierlistJpaRepository.save(TierlistJpaEntity.from(tierlist)).toTierlist();
  }

  @Override
  public Optional<Tierlist> findById(Long id) {
    return tierlistJpaRepository.findById(id).map(TierlistJpaEntity::toTierlist);
  }
}
