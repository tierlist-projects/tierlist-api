package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistCreateService implements TierlistCreateUseCase {

  @Override
  public Long create(String email, TierlistCreateCommand command) {
    return 0L;
  }
}
