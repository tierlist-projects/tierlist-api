package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.tierlist.tierlist.tierlist.application.domain.model.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemRankResponse implements Comparable<ItemRankResponse> {

  private Long id;
  private String name;
  private String itemRankImage;
  private int orderIdx;
  private Rank rank;

  @Override
  public int compareTo(ItemRankResponse o) {
    if (rank.compareTo(o.rank) == 0) {
      return orderIdx - o.orderIdx;
    }

    return rank.compareTo(o.rank);
  }
}
