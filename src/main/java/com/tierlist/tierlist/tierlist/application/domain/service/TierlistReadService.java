package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TierlistReadService implements TierlistReadUseCase {

  @Override
  public TierlistDetailResponse getTierlist(String email, Long tierlistId) {
    return null;
  }

  @Override
  public List<TierlistResponse> getTierlists(String email, int pageCount, int pageSize,
      String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getMyTierlists(String email, int pageCount, int pageSize,
      String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getTierlistsOfCategory(String email, Long categoryId, int pageCount,
      int pageSize, String query, TierlistFilter filter) {
    return List.of();
  }

  @Override
  public List<TierlistResponse> getTierlistsOfTopic(String email, Long topicId, int pageCount,
      int pageSize, String query, TierlistFilter filter) {
    return List.of();
  }
}
