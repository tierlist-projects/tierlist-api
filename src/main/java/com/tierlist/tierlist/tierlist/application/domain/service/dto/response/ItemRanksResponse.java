package com.tierlist.tierlist.tierlist.application.domain.service.dto.response;

import com.tierlist.tierlist.tierlist.application.domain.model.Rank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRanksResponse {

  private List<ItemRankResponse> sRanks;
  private List<ItemRankResponse> aRanks;
  private List<ItemRankResponse> bRanks;
  private List<ItemRankResponse> cRanks;
  private List<ItemRankResponse> dRanks;
  private List<ItemRankResponse> fRanks;
  private List<ItemRankResponse> noneRanks;

  public ItemRanksResponse(List<ItemRankResponse> itemRankResponses) {
    sRanks = getItemRankResponses(itemRankResponses, Rank.S);
    aRanks = getItemRankResponses(itemRankResponses, Rank.A);
    bRanks = getItemRankResponses(itemRankResponses, Rank.B);
    cRanks = getItemRankResponses(itemRankResponses, Rank.C);
    dRanks = getItemRankResponses(itemRankResponses, Rank.D);
    fRanks = getItemRankResponses(itemRankResponses, Rank.F);
    noneRanks = getItemRankResponses(itemRankResponses, Rank.NONE);
  }

  private static List<ItemRankResponse> getItemRankResponses(
      List<ItemRankResponse> itemRankResponses, Rank rank) {
    return itemRankResponses.stream()
        .filter(itemRankResponse -> itemRankResponse.getRank().equals(rank)).sorted().toList();
  }
}
