package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import org.springframework.data.domain.Pageable;

public interface TierlistCommentReadUseCase {

  PageResponse<TierlistCommentResponse> getTierlistComments(String email, Long tierlistId,
      Pageable pageable);

}
