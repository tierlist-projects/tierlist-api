package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentReadUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TierlistCommentReadService implements TierlistCommentReadUseCase {

  @Override
  public List<TierlistCommentResponse> getTierlistComments(String email, Long tierlistId,
      int pageCount, int pageSize) {
    return List.of();
  }
}
