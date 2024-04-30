package com.tierlist.tierlist.topic.application.port.out.persistence;


import com.tierlist.tierlist.topic.application.domain.model.Topic;
import java.util.Optional;

public interface TopicRepository {

  Topic save(Topic topic);

  Optional<Topic> findById(Long topicId);

  boolean existsByNameInCategory(Long categoryId, String name);
}
