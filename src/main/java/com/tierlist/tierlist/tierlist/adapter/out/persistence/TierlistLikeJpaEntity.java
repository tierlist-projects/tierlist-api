package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistLike;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tierlistLike")
@Entity
public class TierlistLikeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long memberId;

  private Long tierlistId;

  public static TierlistLikeJpaEntity from(TierlistLike tierlistLike) {
    return TierlistLikeJpaEntity.builder()
        .id(tierlistLike.getId())
        .memberId(tierlistLike.getMemberId())
        .tierlistId(tierlistLike.getTierlistId())
        .build();
  }

  public TierlistLike toTierlistLike() {
    return TierlistLike.builder()
        .id(id)
        .memberId(memberId)
        .tierlistId(tierlistId)
        .build();
  }

}
