package com.tierlist.tierlist.tierlist.application.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TierlistComment {

  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  private Long tierlistId;
  private Long writerId;
  private Long rootId;
  private Long parentCommentId;

  @Builder
  public TierlistComment(Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt,
      Long tierlistId, Long writerId, Long parentCommentId) {
    this.id = id;
    this.content = content;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.tierlistId = tierlistId;
    this.writerId = writerId;

    bindRootId(parentCommentId);
  }

  private void bindRootId(Long parentCommentId) {
    if (parentCommentId == null) {
      this.rootId = this.id;
    } else {
      this.rootId = parentCommentId;
      this.parentCommentId = parentCommentId;
    }
  }
}
