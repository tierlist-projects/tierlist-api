package com.tierlist.tierlist.topic.application.domain.model.command;

import com.tierlist.tierlist.topic.application.domain.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TopicCreateCommand {

  private Long categoryId;
  private String name;

  public Topic toTopic() {
    return Topic.builder()
        .categoryId(categoryId)
        .name(name)
        .build();
  }
}
