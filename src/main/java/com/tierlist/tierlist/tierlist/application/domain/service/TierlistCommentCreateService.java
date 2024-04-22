package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCommandCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class TierlistCommentCreateService implements TierlistCommentCreateUseCase {


  @Override
  public Long createComment(String email, Long tierlistId, TierlistCommandCreateCommand command) {
    return 0L;
  }
}
