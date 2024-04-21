package com.tierlist.tierlist.topic.application.port.in.service;

import com.tierlist.tierlist.topic.application.domain.model.command.TopicCreateCommand;

public interface TopicCreateUseCase {

  Long create(TopicCreateCommand command);
}
