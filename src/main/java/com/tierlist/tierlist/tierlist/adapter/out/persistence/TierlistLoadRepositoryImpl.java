package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import static com.tierlist.tierlist.item.adapter.out.persistence.QItemJpaEntity.itemJpaEntity;
import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QItemRankJpaEntity.itemRankJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QTierlistJpaEntity.tierlistJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QTierlistLikeJpaEntity.tierlistLikeJpaEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.member.adapter.out.persistence.MemberJpaEntity;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRankResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRanksResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLoadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistLoadRepositoryImpl implements TierlistLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public TierlistDetailResponse loadTierlistById(String viewerEmail, Long tierlistId) {
    MemberJpaEntity member = jpaQueryFactory.selectFrom(memberJpaEntity)
        .where(memberJpaEntity.email.eq(viewerEmail)).fetchFirst();

    TierlistDetailResponse tierlistDetailResponse = jpaQueryFactory.select(
            Projections.constructor(TierlistDetailResponse.class,
                tierlistJpaEntity.id,
                tierlistJpaEntity.title,
                tierlistJpaEntity.content,
                memberJpaEntity.id,
                memberJpaEntity.email,
                memberJpaEntity.profileImage,
                memberJpaEntity.email.eq(viewerEmail),
                tierlistJpaEntity.isPublished,
                tierlistLikeJpaEntity.isNotNull(),
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                tierlistJpaEntity.createdAt))
        .from(tierlistJpaEntity)
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistJpaEntity.id.eq(tierlistLikeJpaEntity.tierlistId),
            tierlistJpaEntity.memberId.eq(member.getId()))
        .where(tierlistJpaEntity.id.eq(tierlistId))
        .leftJoin(memberJpaEntity)
        .on(memberJpaEntity.id.eq(tierlistJpaEntity.memberId))
        .fetchFirst();

    List<ItemRankResponse> itemRankResponses = jpaQueryFactory.select(
            Projections.constructor(ItemRankResponse.class,
                itemJpaEntity.id,
                itemJpaEntity.name,
                itemRankJpaEntity.image,
                itemRankJpaEntity.orderIdx,
                itemRankJpaEntity.rank
            )).from(itemRankJpaEntity)
        .join(itemJpaEntity)
        .on(itemJpaEntity.id.eq(itemRankJpaEntity.itemId))
        .where(itemRankJpaEntity.tierlistId.eq(tierlistId))
        .fetch();

    tierlistDetailResponse.setRanks(new ItemRanksResponse(itemRankResponses));

    return tierlistDetailResponse;
  }
}
