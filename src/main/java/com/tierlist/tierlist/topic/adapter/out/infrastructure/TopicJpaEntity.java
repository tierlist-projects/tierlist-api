package com.tierlist.tierlist.topic.adapter.out.infrastructure;


import com.tierlist.tierlist.topic.application.domain.model.Topic;
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
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "topic")
@Entity
public class TopicJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private String name;

  private Long categoryId;

  @ColumnDefault("0")
  private int favoriteCount;

  public static TopicJpaEntity from(Topic topic) {
    return TopicJpaEntity.builder()
        .id(topic.getId())
        .categoryId(topic.getCategoryId())
        .name(topic.getName())
        .favoriteCount(topic.getFavoriteCount())
        .build();
  }

  public Topic toTopic() {
    return Topic.builder()
        .id(id)
        .categoryId(categoryId)
        .name(name)
        .favoriteCount(favoriteCount)
        .build();
  }
}
