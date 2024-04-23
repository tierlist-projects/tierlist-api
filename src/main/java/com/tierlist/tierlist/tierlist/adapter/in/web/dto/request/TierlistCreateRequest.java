package com.tierlist.tierlist.tierlist.adapter.in.web.dto.request;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCreateCommand;
import com.tierlist.tierlist.tierlist.application.domain.validation.TierlistTitle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TierlistCreateRequest {

  private Long topicId;

  @TierlistTitle
  private String title;

  public TierlistCreateCommand toCommand() {
    return TierlistCreateCommand.builder()
        .topicId(topicId)
        .title(title)
        .build();
  }
}
