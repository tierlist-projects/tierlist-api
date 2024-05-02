package com.tierlist.tierlist.item.adapter.out.persistence;

import static com.tierlist.tierlist.item.adapter.out.persistence.QItemJpaEntity.itemJpaEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemLoadRepository;
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
public class ItemLoadRepositoryImpl implements ItemLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<ItemResponse> getItems(Long categoryId, Pageable pageable, String query) {
    List<ItemResponse> itemResponses = jpaQueryFactory.select(
            Projections.constructor(ItemResponse.class,
                itemJpaEntity.id, itemJpaEntity.name))
        .from(itemJpaEntity)
        .where(itemJpaEntity.categoryId.eq(categoryId), hasQuery(query))
        .orderBy(itemJpaEntity.name.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory.select(itemJpaEntity.count())
        .from(itemJpaEntity)
        .where(itemJpaEntity.categoryId.eq(categoryId), hasQuery(query))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchOne();

    return new PageImpl<>(itemResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  private BooleanExpression hasQuery(String query) {
    return Strings.isBlank(query) ? null : itemJpaEntity.name.contains(query);
  }
}
