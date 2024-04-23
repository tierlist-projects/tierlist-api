package com.tierlist.tierlist.tierlist.adapter.in.web.dto.request;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCommandCreateCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TierlistCommentCreateRequest {

  @NotBlank
  private String content;

  private Long parentCommentId;

  public TierlistCommandCreateCommand toCommand() {
    return TierlistCommandCreateCommand.builder()
        .content(content)
        .parentCommentId(parentCommentId)
        .build();
  }
}
