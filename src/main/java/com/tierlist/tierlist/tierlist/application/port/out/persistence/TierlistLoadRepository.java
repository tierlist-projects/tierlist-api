package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TierlistLoadRepository {

  TierlistDetailResponse loadTierlistById(String viewerEmail, Long tierlistId);

  Page<TierlistResponse> loadTierlists(String viewEmail, Pageable pageable, String query,
      TierlistFilter filter);

  Page<TierlistResponse> getMyTierlists(String viewerEmail, Pageable pageable,
      String query, TierlistFilter filter);

  Page<TierlistResponse> getTierlistsOfCategory(String viewerEmail, Long categoryId,
      Pageable pageable, String query, TierlistFilter filter);

  Page<TierlistResponse> getTierlistsOfTopic(String viewerEmail, Long topicId,
      Pageable pageable, String query, TierlistFilter filter);
}
