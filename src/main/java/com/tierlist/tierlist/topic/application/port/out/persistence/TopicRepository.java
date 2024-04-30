package com.tierlist.tierlist.topic.application.port.out.persistence;


import com.tierlist.tierlist.topic.application.domain.model.Topic;
import java.util.Optional;

public interface TopicRepository {

  Topic save(Topic topic);

  boolean existsByName(String name);

  Optional<Topic> findById(Long topicId);
}
