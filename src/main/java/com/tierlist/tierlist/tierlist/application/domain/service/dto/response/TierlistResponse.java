package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

  private TopicResponse topic;

  private LocalDateTime createdAt;

  private int likesCount;
  private int commentsCount;
  private int viewCount;

  private Boolean isPublished;
  private MemberResponse writer;

}
