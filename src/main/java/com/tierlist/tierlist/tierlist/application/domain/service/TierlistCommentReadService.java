package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentReadUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistCommentLoadRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistCommentReadService implements TierlistCommentReadUseCase {

  private final TierlistCommentLoadRepository tierlistCommentLoadRepository;
  private final TierlistRepository tierlistRepository;

  @Transactional(readOnly = true)
  @Override
  public PageResponse<TierlistCommentResponse> getTierlistComments(String email, Long tierlistId,
      Pageable pageable) {
    if (!tierlistRepository.existsById(tierlistId)) {
      throw new TierlistNotFoundException();
    }

    Page<TierlistCommentResponse> tierlistComments = tierlistCommentLoadRepository.loadTierlistComments(
        email, tierlistId, pageable);

    return PageResponse.fromPage(tierlistComments);
  }
}
