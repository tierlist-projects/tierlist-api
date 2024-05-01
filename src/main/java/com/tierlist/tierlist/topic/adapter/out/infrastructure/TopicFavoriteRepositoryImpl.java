package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicFavoriteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TopicFavoriteRepositoryImpl implements TopicFavoriteRepository {

  private final TopicFavoriteJpaRepository topicFavoriteJpaRepository;

  @Override
  public Optional<TopicFavorite> findByMemberIdAndTopicId(Long memberId, Long topicId) {
    return topicFavoriteJpaRepository.findByMemberIdAndTopicId(memberId, topicId)
        .map(TopicFavoriteJpaEntity::toTopicFavorite);
  }

  @Override
  public TopicFavorite save(TopicFavorite topicFavorite) {
    return topicFavoriteJpaRepository.save(TopicFavoriteJpaEntity.from(topicFavorite))
        .toTopicFavorite();
  }

  @Override
  public void delete(TopicFavorite topicFavorite) {
    topicFavoriteJpaRepository.delete(TopicFavoriteJpaEntity.from(topicFavorite));
  }
}
