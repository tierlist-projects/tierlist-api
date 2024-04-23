package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistReadService implements TierlistReadUseCase {

  @Override
  public TierlistDetailResponse getTierlist(String email, Long tierlistId) {
    return null;
  }
}
