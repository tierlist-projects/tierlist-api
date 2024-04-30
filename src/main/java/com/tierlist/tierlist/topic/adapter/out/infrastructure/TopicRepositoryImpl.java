package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TopicRepositoryImpl implements TopicRepository {

  private final TopicJpaRepository topicJpaRepository;


  @Override
  public Topic save(Topic topic) {
    return topicJpaRepository.save(TopicJpaEntity.from(topic)).toTopic();
  }

  @Override
  public boolean existsByName(String name) {
    return topicJpaRepository.existsByName(name);
  }

  @Override
  public Optional<Topic> findById(Long topicId) {
    return topicJpaRepository.findById(topicId).map(TopicJpaEntity::toTopic);
  }
}
