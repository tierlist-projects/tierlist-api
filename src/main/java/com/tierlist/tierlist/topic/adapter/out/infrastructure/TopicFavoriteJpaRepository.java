package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicFavoriteJpaRepository extends JpaRepository<TopicFavoriteJpaEntity, Long> {

  Optional<TopicFavoriteJpaEntity> findByMemberIdAndTopicId(Long memberId, Long topicId);
}
