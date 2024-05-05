package com.tierlist.tierlist.tierlist.application.domain.model.command;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistCommandCreateCommand {

  private String content;
  private Long parentCommentId;

  public TierlistComment toTierlistComment(Long tierlistId, Long writerId) {
    return TierlistComment.builder()
        .content(content)
        .parentCommentId(parentCommentId)
        .tierlistId(tierlistId)
        .writerId(writerId)
        .build();
  }
}
