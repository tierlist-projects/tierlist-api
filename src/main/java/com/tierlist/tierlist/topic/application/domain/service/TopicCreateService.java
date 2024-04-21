package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.topic.application.domain.model.command.TopicCreateCommand;
import com.tierlist.tierlist.topic.application.port.in.service.TopicCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class TopicCreateService implements TopicCreateUseCase {

  @Override
  public Long create(TopicCreateCommand command) {
    return 0L;
  }
}
