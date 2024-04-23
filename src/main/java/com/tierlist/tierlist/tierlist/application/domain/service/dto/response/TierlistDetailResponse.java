package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistDetailResponse {

  private Long id;

  private String title;
  private String content;

  private LocalDateTime createdAt;

  private List<ItemRankResponse> sRanks;
  private List<ItemRankResponse> aRanks;
  private List<ItemRankResponse> bRanks;
  private List<ItemRankResponse> cRanks;
  private List<ItemRankResponse> dRanks;
  private List<ItemRankResponse> fRanks;
  private List<ItemRankResponse> noneRanks;

  private boolean liked;
  private int likesCount;

  private boolean isMyTierlist;

}
