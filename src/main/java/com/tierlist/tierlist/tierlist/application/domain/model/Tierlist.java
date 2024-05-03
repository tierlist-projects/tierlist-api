package com.tierlist.tierlist.tierlist.application.domain.model;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Tierlist {

  private Long id;

  private String title;

  private String content;

  private Long topicId;

  private Long memberId;

  private boolean isPublished;

  private int likeCount;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  public void togglePublish() {
    isPublished = !isPublished;
  }

  public void addLike() {
    likeCount++;
  }

  public void removeLike() {
    likeCount--;
  }

  public void edit(TierlistEditCommand command) {
    this.title = command.getTitle();
    this.content = command.getContent();
  }
}
