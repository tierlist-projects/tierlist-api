package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;

public interface TierlistReadUseCase {

  TierlistDetailResponse getTierlist(String email, Long tierlistId);
}
