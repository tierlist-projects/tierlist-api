package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import com.tierlist.tierlist.global.common.model.TimeBaseEntity;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistComment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tierlist_comment")
@Entity
public class TierlistCommentJpaEntity extends TimeBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  private Long tierlistId;
  private Long writerId;
  private Long rootId;
  private Long parentCommentId;

  public static TierlistCommentJpaEntity from(TierlistComment tierlistComment) {
    return TierlistCommentJpaEntity.builder()
        .id(tierlistComment.getId())
        .content(tierlistComment.getContent())
        .tierlistId(tierlistComment.getTierlistId())
        .writerId(tierlistComment.getWriterId())
        .rootId(tierlistComment.getRootId())
        .parentCommentId(tierlistComment.getParentCommentId())
        .createdAt(tierlistComment.getCreatedAt())
        .modifiedAt(tierlistComment.getModifiedAt())
        .build();
  }

  public TierlistComment toTierlistComment() {
    return TierlistComment.builder()
        .id(id)
        .content(content)
        .tierlistId(tierlistId)
        .writerId(writerId)
        .parentCommentId(parentCommentId)
        .createdAt(getCreatedAt())
        .modifiedAt(getModifiedAt())
        .build();
  }
}
