package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TierlistCommentResponse {

  private Long id;
  private String content;
  private MemberResponse writer;
  private LocalDateTime createdAt;

  private boolean isMyComment;
  private boolean isParentComment;
  private boolean isTierlistWriter;

  public TierlistCommentResponse(Long id, String content,
      Long writerId, String writerNickname, String writerProfileImage,
      LocalDateTime createdAt, boolean isMyComment, boolean isParentComment,
      boolean isTierlistWriter) {
    this.id = id;
    this.content = content;
    this.writer = MemberResponse.builder()
        .id(writerId)
        .nickname(writerNickname)
        .profileImage(writerProfileImage)
        .build();

    this.createdAt = createdAt;
    this.isMyComment = isMyComment;
    this.isParentComment = isParentComment;
    this.isTierlistWriter = isTierlistWriter;
  }
}
