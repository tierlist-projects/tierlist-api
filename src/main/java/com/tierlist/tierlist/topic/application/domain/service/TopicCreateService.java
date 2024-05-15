package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.topic.application.domain.exception.TopicDuplicationException;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.domain.model.command.TopicCreateCommand;
import com.tierlist.tierlist.topic.application.port.in.service.TopicCreateUseCase;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TopicCreateService implements TopicCreateUseCase {

  private final CategoryRepository categoryRepository;
  private final TopicRepository topicRepository;

  @Transactional
  @Override
  public Long create(TopicCreateCommand command) {
    if (!categoryRepository.existsById(command.getCategoryId())) {
      throw new CategoryNotFoundException();
    }

    if (topicRepository.existsByNameInCategory(command.getCategoryId(), command.getName())) {
      throw new TopicDuplicationException();
    }

    Topic topic = topicRepository.save(command.toTopic());

    return topic.getId();
  }
}
