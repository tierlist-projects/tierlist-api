package com.tierlist.tierlist.tierlist.application.domain.model.command;

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

}
