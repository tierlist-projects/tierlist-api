package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistPublishUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistPublishService implements TierlistPublishUseCase {

  @Override
  public void togglePublish(String email, Long tierlistId) {

  }
}
