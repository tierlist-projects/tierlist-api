package com.tierlist.tierlist.topic.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TopicCreateCommand {

  private Long categoryId;
  private String name;

}
