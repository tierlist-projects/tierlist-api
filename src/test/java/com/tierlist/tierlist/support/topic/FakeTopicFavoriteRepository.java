package com.tierlist.tierlist.support.topic;

import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicFavoriteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeTopicFavoriteRepository implements TopicFavoriteRepository {

  private List<TopicFavorite> data = new ArrayList<>();
  private Long autoGeneratedId = 0L;

  @Override
  public TopicFavorite save(TopicFavorite topicFavorite) {
    Long id = topicFavorite.getId();
    if (id == null || id == 0) {
      TopicFavorite newTopicFavorite = TopicFavorite.builder()
          .id(++autoGeneratedId)
          .memberId(topicFavorite.getMemberId())
          .topicId(topicFavorite.getTopicId())
          .build();
      data.add(newTopicFavorite);
      return newTopicFavorite;
    }

    data.removeIf(item -> item.getId().equals(id));
    data.add(topicFavorite);
    return topicFavorite;
  }

  @Override
  public Optional<TopicFavorite> findByMemberIdAndTopicId(Long memberId, Long topicId) {
    return data.stream()
        .filter(item -> item.getMemberId().equals(memberId) &&
            item.getTopicId().equals(topicId))
        .findFirst();
  }

  @Override
  public void delete(TopicFavorite topicFavorite) {
    data.removeIf(item -> item.getId().equals(topicFavorite.getId()));
  }
}
