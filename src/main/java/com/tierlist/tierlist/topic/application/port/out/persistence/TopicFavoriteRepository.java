package com.tierlist.tierlist.topic.application.port.out.persistence;

import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import java.util.Optional;

public interface TopicFavoriteRepository {

  Optional<TopicFavorite> findByMemberIdAndTopicId(Long memberId, Long topicId);

  TopicFavorite save(TopicFavorite topicFavorite);

  void delete(TopicFavorite topicFavorite);
}
