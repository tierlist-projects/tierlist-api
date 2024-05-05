package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QTierlistCommentJpaEntity.tierlistCommentJpaEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistCommentLoadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistCommentLoadRepositoryImpl implements TierlistCommentLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<TierlistCommentResponse> loadTierlistComments(String viewerEmail, Long tierlistId,
      Pageable pageable) {

    QMemberJpaEntity tierlistWriter = new QMemberJpaEntity("tierlistWriter");

    List<TierlistCommentResponse> tierlistCommentResponses = jpaQueryFactory.select(
            Projections.constructor(
                TierlistCommentResponse.class,
                tierlistCommentJpaEntity.id,
                tierlistCommentJpaEntity.content,
                memberJpaEntity.id,
                memberJpaEntity.nickname,
                memberJpaEntity.profileImage,
                tierlistCommentJpaEntity.createdAt,
                memberJpaEntity.email.eq(viewerEmail).as("isMyComment"),
                tierlistCommentJpaEntity.parentCommentId.isNull().as("isParentComment"),
                tierlistWriter.email.eq(viewerEmail).as("isTierlistWriter")
            ))
        .from(tierlistCommentJpaEntity)
        .join(memberJpaEntity)
        .on(tierlistCommentJpaEntity.writerId.eq(memberJpaEntity.id))
        .join(tierlistWriter)
        .on(tierlistWriter.id.eq(tierlistCommentJpaEntity.writerId))
        .where(tierlistCommentJpaEntity.tierlistId.eq(tierlistId))
        .orderBy(tierlistCommentJpaEntity.rootId.asc(), tierlistCommentJpaEntity.id.asc())
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .fetch();

    Long count = jpaQueryFactory.select(tierlistCommentJpaEntity.count())
        .from(tierlistCommentJpaEntity)
        .where(tierlistCommentJpaEntity.tierlistId.eq(tierlistId))
        .fetchFirst();

    return new PageImpl<>(tierlistCommentResponses, pageable, count);
  }
}
