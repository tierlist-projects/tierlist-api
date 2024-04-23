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
  private MemberResponse writer;
  private LocalDateTime createdAt;
  private String content;

  private boolean liked;
  private int likesCount;

  private boolean isMyComment;
  private boolean isParentComment;
  private boolean isTierlistWriter;

}
