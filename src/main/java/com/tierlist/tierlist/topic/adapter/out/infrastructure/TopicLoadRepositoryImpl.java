package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryJpaEntity.categoryJpaEntity;
import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;
import static com.tierlist.tierlist.topic.adapter.out.infrastructure.QTopicFavoriteJpaEntity.topicFavoriteJpaEntity;
import static com.tierlist.tierlist.topic.adapter.out.infrastructure.QTopicJpaEntity.topicJpaEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicLoadRepository;
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
public class TopicLoadRepositoryImpl implements TopicLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private static OrderSpecifier<?>[] applyFilter(TopicFilter filter) {
    return filter.equals(TopicFilter.HOT) ?
        new OrderSpecifier<?>[]{
            topicJpaEntity.favoriteCount.desc(),
            topicJpaEntity.name.asc()
        } :
        new OrderSpecifier<?>[]{
            topicJpaEntity.name.asc()
        };
  }

  private static BooleanExpression applyQuery(String query) {
    return Strings.isBlank(query) ? null : categoryJpaEntity.name.contains(query);
  }

  @Override
  public Page<TopicResponse> loadFavoriteTopics(String email, Pageable pageable) {
    List<TopicResponse> topicResponses = jpaQueryFactory.select(
            Projections.constructor(TopicResponse.class,
                topicJpaEntity.id,
                topicJpaEntity.name,
                topicJpaEntity.favoriteCount,
                categoryJpaEntity.id,
                categoryJpaEntity.name,
                categoryJpaEntity.favoriteCount
            ))
        .from(topicJpaEntity)
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(topicFavoriteJpaEntity)
        .on(topicJpaEntity.id.eq(topicFavoriteJpaEntity.topicId))
        .join(memberJpaEntity)
        .on(topicFavoriteJpaEntity.memberId.eq(memberJpaEntity.id))
        .where(memberJpaEntity.email.eq(email))
        .orderBy(categoryJpaEntity.name.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(
            topicJpaEntity.count()
        )
        .from(topicJpaEntity)
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .join(topicFavoriteJpaEntity)
        .on(topicJpaEntity.id.eq(topicFavoriteJpaEntity.topicId))
        .join(memberJpaEntity)
        .on(topicFavoriteJpaEntity.memberId.eq(memberJpaEntity.id))
        .where(memberJpaEntity.email.eq(email))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchOne();

    return new PageImpl<>(topicResponses, pageable, Objects.isNull(count) ? 0 : count);

  }

  @Override
  public Page<TopicResponse> loadTopics(String email, Long categoryId, Pageable pageable,
      String query,
      TopicFilter filter) {
    List<TopicResponse> topicResponses = jpaQueryFactory.select(
            Projections.constructor(TopicResponse.class,
                topicJpaEntity.id,
                topicJpaEntity.name,
                topicJpaEntity.favoriteCount,
                categoryJpaEntity.id,
                categoryJpaEntity.name,
                categoryJpaEntity.favoriteCount
            ))
        .from(topicJpaEntity)
        .join(categoryJpaEntity)
        .on(topicJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .where(hasCategoryId(categoryId), applyQuery(query))
        .orderBy(applyFilter(filter))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(
            topicJpaEntity.count()
        )
        .from(topicJpaEntity)
        .where(hasCategoryId(categoryId), applyQuery(query))
        .fetchOne();

    return new PageImpl<>(topicResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  private BooleanExpression hasCategoryId(Long categoryId) {
    return Objects.isNull(categoryId) ? null : categoryJpaEntity.id.eq(categoryId);
  }
}
