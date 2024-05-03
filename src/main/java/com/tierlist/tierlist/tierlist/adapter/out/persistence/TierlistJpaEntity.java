package com.tierlist.tierlist.tierlist.adapter.out.persistence;


import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tierlist")
@Entity
public class TierlistJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(nullable = false)
  private String title;

  private Long memberId;

  private Long topicId;

  private boolean isPublished;

  public static TierlistJpaEntity from(Tierlist tierlist) {
    return TierlistJpaEntity.builder()
        .id(tierlist.getId())
        .title(tierlist.getTitle())
        .memberId(tierlist.getMemberId())
        .topicId(tierlist.getTopicId())
        .isPublished(tierlist.isPublished())
        .build();
  }

  public Tierlist toTierlist() {
    return Tierlist.builder()
        .id(id)
        .title(title)
        .memberId(memberId)
        .topicId(topicId)
        .isPublished(isPublished)
        .build();
  }
}
