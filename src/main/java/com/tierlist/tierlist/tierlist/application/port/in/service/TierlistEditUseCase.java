package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;

public interface TierlistEditUseCase {

  void editTierlist(String email, Long tierlistId, TierlistEditCommand command);

}
