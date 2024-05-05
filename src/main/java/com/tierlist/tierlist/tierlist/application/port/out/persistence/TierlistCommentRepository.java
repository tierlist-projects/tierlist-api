package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import java.util.Optional;

public interface TierlistCommentRepository {

  TierlistComment save(TierlistComment tierlistComment);

  Optional<TierlistComment> findById(Long tierlistCommentId);
}
