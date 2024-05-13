package com.tierlist.tierlist.tierlist.adapter.out.persistence;

import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryJpaEntity.categoryJpaEntity;
import static com.tierlist.tierlist.item.adapter.out.persistence.QItemJpaEntity.itemJpaEntity;
import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QItemRankJpaEntity.itemRankJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QTierlistJpaEntity.tierlistJpaEntity;
import static com.tierlist.tierlist.tierlist.adapter.out.persistence.QTierlistLikeJpaEntity.tierlistLikeJpaEntity;
import static com.tierlist.tierlist.topic.adapter.out.infrastructure.QTopicJpaEntity.topicJpaEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.member.adapter.out.persistence.MemberJpaEntity;
import com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRankResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.ItemRanksResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistLoadRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TierlistLoadRepositoryImpl implements TierlistLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;


  private BooleanExpression hasQuery(String query) {
    if (Strings.isBlank(query)) {
      return null;
    }

    return tierlistJpaEntity.title.contains(query).or(
        tierlistJpaEntity.content.isNotNull().and(
            tierlistJpaEntity.content.contains(query)
        ));
  }

  private OrderSpecifier<?>[] orderByFilter(TierlistFilter filter) {
    return filter.equals(TierlistFilter.HOT) ?
        new OrderSpecifier<?>[]{
            tierlistJpaEntity.likeCount.desc(),
            tierlistJpaEntity.createdAt.desc(),
        } :
        new OrderSpecifier<?>[]{
            tierlistJpaEntity.createdAt.desc()
        };
  }

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
                memberJpaEntity.nickname,
                memberJpaEntity.profileImage,
                topicJpaEntity.id,
                topicJpaEntity.name,
                categoryJpaEntity.id,
                categoryJpaEntity.name,
                tierlistJpaEntity.isPublished,
                memberJpaEntity.email.eq(viewerEmail),
                tierlistLikeJpaEntity.isNotNull(),
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                tierlistJpaEntity.createdAt))
        .from(tierlistJpaEntity)
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistJpaEntity.id.eq(tierlistLikeJpaEntity.tierlistId),
            tierlistJpaEntity.memberId.eq(member.getId()))
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
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

  @Override
  public Page<TierlistResponse> loadTierlists(String viewerEmail, Pageable pageable,
      String query,
      TierlistFilter filter) {
    QMemberJpaEntity viewer = new QMemberJpaEntity("viewer");
    QMemberJpaEntity writer = new QMemberJpaEntity("writer");

    List<TierlistResponse> tierlistResponses = jpaQueryFactory.select(
            Projections.constructor(TierlistResponse.class,
                tierlistJpaEntity.id,
                tierlistJpaEntity.title,
                tierlistJpaEntity.createdAt,
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                viewer.email.isNotNull().as("liked"),
                tierlistJpaEntity.isPublished,
                writer.id,
                writer.nickname,
                writer.profileImage,
                topicJpaEntity.id,
                topicJpaEntity.name,
                categoryJpaEntity.id,
                categoryJpaEntity.name
            ))
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(writer)
        .on(tierlistJpaEntity.memberId.eq(writer.id))
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistLikeJpaEntity.tierlistId.eq(tierlistJpaEntity.id))
        .leftJoin(viewer)
        .on(viewer.email.eq(viewerEmail), viewer.id.eq(tierlistLikeJpaEntity.tierlistId))
        .where(hasQuery(query), tierlistJpaEntity.isPublished)
        .orderBy(orderByFilter(filter))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(tierlistJpaEntity.count())
        .from(tierlistJpaEntity)
        .where(hasQuery(query), tierlistJpaEntity.isPublished)
        .fetchOne();

    return new PageImpl<>(tierlistResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  @Override
  public Page<TierlistResponse> loadMyTierlists(String email, Pageable pageable, String query,
      TierlistFilter filter) {
    QMemberJpaEntity viewer = new QMemberJpaEntity("viewer");
    QMemberJpaEntity writer = new QMemberJpaEntity("writer");

    List<TierlistResponse> tierlistResponses = jpaQueryFactory.select(
            Projections.constructor(TierlistResponse.class,
                tierlistJpaEntity.id,
                tierlistJpaEntity.title,
                tierlistJpaEntity.createdAt,
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                viewer.email.isNotNull().as("liked"),
                tierlistJpaEntity.isPublished,
                writer.id,
                writer.nickname,
                writer.profileImage,
                topicJpaEntity.id,
                topicJpaEntity.name,
                categoryJpaEntity.id,
                categoryJpaEntity.name
            ))
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(writer)
        .on(tierlistJpaEntity.memberId.eq(writer.id))
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistLikeJpaEntity.tierlistId.eq(tierlistJpaEntity.id))
        .leftJoin(viewer)
        .on(viewer.email.eq(email), viewer.id.eq(tierlistLikeJpaEntity.tierlistId))
        .where(writer.email.eq(email), hasQuery(query))
        .orderBy(orderByFilter(filter))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(tierlistJpaEntity.count())
        .from(tierlistJpaEntity)
        .join(writer)
        .on(tierlistJpaEntity.memberId.eq(writer.id))
        .where(writer.email.eq(email), hasQuery(query))
        .fetchOne();

    return new PageImpl<>(tierlistResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  @Override
  public Page<TierlistResponse> loadTierlistsOfCategory(String viewerEmail, Long categoryId,
      Pageable pageable, String query, TierlistFilter filter) {
    QMemberJpaEntity viewer = new QMemberJpaEntity("viewer");
    QMemberJpaEntity writer = new QMemberJpaEntity("writer");

    List<TierlistResponse> tierlistResponses = jpaQueryFactory.select(
            Projections.constructor(TierlistResponse.class,
                tierlistJpaEntity.id,
                tierlistJpaEntity.title,
                tierlistJpaEntity.createdAt,
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                viewer.email.isNotNull().as("liked"),
                tierlistJpaEntity.isPublished,
                writer.id,
                writer.nickname,
                writer.profileImage,
                topicJpaEntity.id,
                topicJpaEntity.name,
                categoryJpaEntity.id,
                categoryJpaEntity.name
            ))
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(writer)
        .on(tierlistJpaEntity.memberId.eq(writer.id))
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistLikeJpaEntity.tierlistId.eq(tierlistJpaEntity.id))
        .leftJoin(viewer)
        .on(viewer.email.eq(viewerEmail), viewer.id.eq(tierlistLikeJpaEntity.tierlistId))
        .where(categoryJpaEntity.id.eq(categoryId), hasQuery(query), tierlistJpaEntity.isPublished)
        .orderBy(orderByFilter(filter))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(tierlistJpaEntity.count())
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .where(categoryJpaEntity.id.eq(categoryId), hasQuery(query), tierlistJpaEntity.isPublished)
        .fetchOne();

    return new PageImpl<>(tierlistResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  @Override
  public Page<TierlistResponse> loadTierlistsOfTopic(String viewerEmail, Long topicId,
      Pageable pageable, String query, TierlistFilter filter) {
    QMemberJpaEntity viewer = new QMemberJpaEntity("viewer");
    QMemberJpaEntity writer = new QMemberJpaEntity("writer");

    List<TierlistResponse> tierlistResponses = jpaQueryFactory.select(
            Projections.constructor(TierlistResponse.class,
                tierlistJpaEntity.id,
                tierlistJpaEntity.title,
                tierlistJpaEntity.createdAt,
                tierlistJpaEntity.likeCount,
                tierlistJpaEntity.commentCount,
                viewer.email.isNotNull().as("liked"),
                tierlistJpaEntity.isPublished,
                writer.id,
                writer.nickname,
                writer.profileImage,
                topicJpaEntity.id,
                topicJpaEntity.name,
                categoryJpaEntity.id,
                categoryJpaEntity.name
            ))
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(writer)
        .on(tierlistJpaEntity.memberId.eq(writer.id))
        .leftJoin(tierlistLikeJpaEntity)
        .on(tierlistLikeJpaEntity.tierlistId.eq(tierlistJpaEntity.id))
        .leftJoin(viewer)
        .on(viewer.email.eq(viewerEmail), viewer.id.eq(tierlistLikeJpaEntity.tierlistId))
        .where(topicJpaEntity.id.eq(topicId), hasQuery(query), tierlistJpaEntity.isPublished)
        .orderBy(orderByFilter(filter))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(tierlistJpaEntity.count())
        .from(tierlistJpaEntity)
        .join(topicJpaEntity)
        .on(tierlistJpaEntity.topicId.eq(topicJpaEntity.id))
        .where(topicJpaEntity.id.eq(topicId), hasQuery(query), tierlistJpaEntity.isPublished)
        .fetchOne();

    return new PageImpl<>(tierlistResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

}
