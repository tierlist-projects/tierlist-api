package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCreateCommand;

public interface TierlistCreateUseCase {

  Long create(String email, TierlistCreateCommand command);

}
