package com.tierlist.tierlist.tierlist.adapter.out.persistence;


import com.tierlist.tierlist.global.common.model.TimeBaseEntity;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tierlist")
@Entity
public class TierlistJpaEntity extends TimeBaseEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(nullable = false)
  private String title;

  private String content;

  private Long memberId;

  private Long topicId;

  private boolean isPublished;

  private int likeCount;
  private int commentCount;

  public static TierlistJpaEntity from(Tierlist tierlist) {
    return TierlistJpaEntity.builder()
        .id(tierlist.getId())
        .title(tierlist.getTitle())
        .content(tierlist.getContent())
        .memberId(tierlist.getMemberId())
        .topicId(tierlist.getTopicId())
        .isPublished(tierlist.isPublished())
        .likeCount(tierlist.getLikeCount())
        .createdAt(tierlist.getCreatedAt())
        .modifiedAt(tierlist.getModifiedAt())
        .build();
  }

  public Tierlist toTierlist() {
    return Tierlist.builder()
        .id(id)
        .title(title)
        .content(content)
        .memberId(memberId)
        .topicId(topicId)
        .isPublished(isPublished)
        .likeCount(likeCount)
        .createdAt(getCreatedAt())
        .modifiedAt(getModifiedAt())
        .build();
  }
}
