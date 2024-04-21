package com.tierlist.tierlist.topic.adapter.in.web.dto.request;

import com.tierlist.tierlist.topic.application.domain.model.command.TopicCreateCommand;
import com.tierlist.tierlist.topic.application.domain.validation.TopicName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicCreateRequest {

  private Long categoryId;

  @TopicName
  private String name;

  public TopicCreateCommand toCommand() {
    return TopicCreateCommand.builder()
        .categoryId(categoryId)
        .name(name)
        .build();
  }
}
