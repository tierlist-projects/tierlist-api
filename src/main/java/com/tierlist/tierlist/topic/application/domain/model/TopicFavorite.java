package com.tierlist.tierlist.topic.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TopicFavorite {

  private Long id;
  private Long topicId;
  private Long memberId;

}
