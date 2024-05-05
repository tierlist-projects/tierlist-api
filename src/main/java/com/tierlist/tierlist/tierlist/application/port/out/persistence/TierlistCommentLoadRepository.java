package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TierlistCommentLoadRepository {

  Page<TierlistCommentResponse> loadTierlistComments(String viewerEmail, Long tierlistId,
      Pageable pageable);
}
