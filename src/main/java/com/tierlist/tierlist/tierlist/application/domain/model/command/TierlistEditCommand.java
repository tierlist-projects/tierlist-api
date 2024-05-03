package com.tierlist.tierlist.tierlist.application.domain.model.command;

import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import com.tierlist.tierlist.tierlist.application.domain.model.Rank;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TierlistEditCommand {

  private String title;

  private List<ItemRankCommand> sRanks;
  private List<ItemRankCommand> aRanks;
  private List<ItemRankCommand> bRanks;
  private List<ItemRankCommand> cRanks;
  private List<ItemRankCommand> dRanks;
  private List<ItemRankCommand> fRanks;
  private List<ItemRankCommand> noneRanks;

  private String content;

  public List<ItemRank> toItemRanks(Long tierlistId) {
    List<ItemRank> ret = new ArrayList<>();
    ret.addAll(toItemRanks(sRanks, Rank.S, tierlistId));
    ret.addAll(toItemRanks(aRanks, Rank.A, tierlistId));
    ret.addAll(toItemRanks(bRanks, Rank.B, tierlistId));
    ret.addAll(toItemRanks(cRanks, Rank.C, tierlistId));
    ret.addAll(toItemRanks(dRanks, Rank.D, tierlistId));
    ret.addAll(toItemRanks(fRanks, Rank.F, tierlistId));
    ret.addAll(toItemRanks(noneRanks, Rank.NONE, tierlistId));
    return ret;
  }

  private List<ItemRank> toItemRanks(List<ItemRankCommand> ranks, Rank rank, Long tierlistId) {
    List<ItemRank> ret = new ArrayList<>();
    for (int idx = 0; idx < ranks.size(); ++idx) {
      ItemRankCommand itemRankCommand = ranks.get(idx);
      ret.add(ItemRank.builder()
          .rank(rank)
          .tierlistId(tierlistId)
          .itemId(itemRankCommand.getItemId())
          .image(itemRankCommand.getItemRankImage())
          .order(idx)
          .build());
    }

    return ret;
  }
}
