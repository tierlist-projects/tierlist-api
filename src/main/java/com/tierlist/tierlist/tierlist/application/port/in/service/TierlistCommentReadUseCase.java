package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import java.util.List;

public interface TierlistCommentReadUseCase {

  List<TierlistCommentResponse> getTierlistComments(String email, Long tierlistId,
      int pageCount, int pageSize);

}
