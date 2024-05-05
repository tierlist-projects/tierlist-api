package com.tierlist.tierlist.tierlist.application.port.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;

public interface TierlistLoadRepository {

  TierlistDetailResponse loadTierlistById(String viewerEmail, Long tierlistId);
}
