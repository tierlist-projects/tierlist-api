package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class TierlistResponse {

  private Long id;
  private String title;
  private String thumbnailImage;

  private TopicResponse topic;

  private MemberResponse writer;

  private LocalDateTime createdAt;

  private int likesCount;
  private int commentsCount;

  private Boolean liked;
  private Boolean isPublished;

  public TierlistResponse(Long id, String title, String thumbnailImage, LocalDateTime createdAt,
      int likesCount, int commentsCount, Boolean liked, Boolean isPublished,
      Long writerId, String writerNickname, String writerProfileImage,
      Long topicId, String topicName,
      Long categoryId, String categoryName) {
    this.id = id;
    this.title = title;
    this.thumbnailImage = thumbnailImage;
    this.createdAt = createdAt;
    this.likesCount = likesCount;
    this.commentsCount = commentsCount;
    this.isPublished = isPublished;
    this.liked = liked;

    this.writer = MemberResponse.builder()
        .id(writerId)
        .nickname(writerNickname)
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
  }
}
