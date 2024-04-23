package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import java.util.List;

public interface TierlistReadUseCase {

  TierlistDetailResponse getTierlist(String email, Long tierlistId);

  List<TierlistResponse> getTierlists(String email, int pageCount, int pageSize, String query,
      TierlistFilter filter);

  List<TierlistResponse> getMyTierlists(String email, int pageCount, int pageSize, String query,
      TierlistFilter filter);

  List<TierlistResponse> getTierlistsOfCategory(String email, Long categoryId, int pageCount,
      int pageSize, String query, TierlistFilter filter);

  List<TierlistResponse> getTierlistsOfTopic(String email, Long topicId, int pageCount,
      int pageSize, String query, TierlistFilter filter);
}
