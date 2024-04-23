package com.tierlist.tierlist.tierlist.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistCreateCommand {

  private Long topicId;
  private String title;

}
