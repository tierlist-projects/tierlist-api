package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import java.util.Optional;

public interface TierlistRepository {

  Tierlist save(Tierlist tierlist);

  Optional<Tierlist> findById(Long id);

  boolean existsById(Long id);
}
