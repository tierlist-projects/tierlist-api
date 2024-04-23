package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistEditUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistEditService implements TierlistEditUseCase {

  @Override
  public void editTierlist(String email, Long tierlistId, TierlistEditCommand command) {

  }
}
