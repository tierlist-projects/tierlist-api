package com.tierlist.tierlist.tierlist.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Tierlist {

  private Long id;

  private String title;

  private Long topicId;

  private Long memberId;

  private boolean isPublished;

  private int likeCount;

  public void togglePublish() {
    isPublished = !isPublished;
  }

  public void addLike() {
    likeCount++;
  }

  public void removeLike() {
    likeCount--;
  }

}
