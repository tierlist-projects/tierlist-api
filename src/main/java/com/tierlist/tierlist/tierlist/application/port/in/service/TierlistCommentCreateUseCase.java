package com.tierlist.tierlist.tierlist.application.port.in.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCommandCreateCommand;

public interface TierlistCommentCreateUseCase {

  Long createComment(String email, Long tierlistId, TierlistCommandCreateCommand command);

}
