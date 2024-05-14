package com.tierlist.tierlist.tierlist.adapter.in.web.dto.request;

import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TierlistEditRequest {

  @NotBlank
  private String title;

  @NotBlank
  private String content;

  private String thumbnailImage;

  @NotNull
  private List<ItemRankDto> sRanks;

  @NotNull
  private List<ItemRankDto> aRanks;

  @NotNull
  private List<ItemRankDto> bRanks;

  @NotNull
  private List<ItemRankDto> cRanks;

  @NotNull
  private List<ItemRankDto> dRanks;

  @NotNull
  private List<ItemRankDto> fRanks;

  @NotNull
  private List<ItemRankDto> noneRanks;

  public TierlistEditCommand toCommand() {
    return TierlistEditCommand.builder()
        .title(title)
        .thumbnailImage(thumbnailImage)
        .sRanks(sRanks.stream().map(ItemRankDto::toCommand).toList())
        .aRanks(aRanks.stream().map(ItemRankDto::toCommand).toList())
        .bRanks(bRanks.stream().map(ItemRankDto::toCommand).toList())
        .cRanks(cRanks.stream().map(ItemRankDto::toCommand).toList())
        .dRanks(dRanks.stream().map(ItemRankDto::toCommand).toList())
        .fRanks(fRanks.stream().map(ItemRankDto::toCommand).toList())
        .noneRanks(noneRanks.stream().map(ItemRankDto::toCommand).toList())
        .content(content)
        .build();
  }
}
