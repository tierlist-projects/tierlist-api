package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "topicFavorite")
@Entity
public class TopicFavoriteJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private Long memberId;

  private Long topicId;

  public static TopicFavoriteJpaEntity from(TopicFavorite topicFavorite) {
    return TopicFavoriteJpaEntity.builder()
        .id(topicFavorite.getId())
        .topicId(topicFavorite.getTopicId())
        .memberId(topicFavorite.getMemberId())
        .build();
  }

  public TopicFavorite toTopicFavorite() {
    return TopicFavorite.builder()
        .id(id)
        .topicId(topicId)
        .memberId(memberId)
        .build();
  }
}
