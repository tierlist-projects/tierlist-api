package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import org.springframework.data.domain.Pageable;

public interface TierlistReadUseCase {

  TierlistDetailResponse getTierlist(String email, Long tierlistId);

  PageResponse<TierlistResponse> getTierlists(String email, Pageable pageable,
      String query,
      TierlistFilter filter);

  PageResponse<TierlistResponse> getMyTierlists(String email, Pageable pageable,
      String query,
      TierlistFilter filter);

  PageResponse<TierlistResponse> getTierlistsOfCategory(String email, Long categoryId,
      Pageable pageable, String query, TierlistFilter filter);

  PageResponse<TierlistResponse> getTierlistsOfTopic(String email, Long topicId, Pageable pageable,
      String query, TierlistFilter filter);
}
