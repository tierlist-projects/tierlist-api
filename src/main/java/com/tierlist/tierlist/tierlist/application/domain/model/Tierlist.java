package com.tierlist.tierlist.tierlist.application.domain.model;

import com.tierlist.tierlist.member.application.domain.model.Member;
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

  private String thumbnailImage;

  private Long topicId;

  private Long memberId;

  private boolean isPublished;

  private int likeCount;

  private int commentCount;

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
    this.thumbnailImage = command.getThumbnailImage();
  }

  public boolean canView(Member member) {
    return member.getId().equals(memberId) || isPublished;
  }

  public boolean canAddComment() {
    return isPublished;
  }

  public void addComment() {
    commentCount++;
  }
}
