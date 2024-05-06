package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistDetailResponse {

  private Long id;

  private String title;
  private String content;

  private MemberResponse writer;
  private TopicResponse topic;

  private boolean isPublished;
  private boolean isMyTierlist;
  private boolean liked;

  private int likesCount;
  private int commentsCount;

  private LocalDateTime createdAt;

  @Setter
  private ItemRanksResponse ranks;

  public TierlistDetailResponse(Long id, String title, String content,
      Long writerId, String writerEmail, String writerProfileImage,
      Long topicId, String topicName,
      Long categoryId, String categoryName,
      boolean isPublished, boolean isMyTierlist, boolean liked, int likesCount, int commentsCount,
      LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.writer = MemberResponse.builder()
        .id(writerId)
        .email(writerEmail)
        .profileImage(writerProfileImage)
        .build();
    this.topic = TopicResponse.builder()
        .id(topicId)
        .name(topicName)
        .category(CategoryResponse.builder()
            .id(categoryId)
            .name(categoryName)
            .build())
        .build();
    this.isPublished = isPublished;
    this.isMyTierlist = isMyTierlist;
    this.liked = liked;
    this.likesCount = likesCount;
    this.commentsCount = commentsCount;
    this.createdAt = createdAt;
  }

}
