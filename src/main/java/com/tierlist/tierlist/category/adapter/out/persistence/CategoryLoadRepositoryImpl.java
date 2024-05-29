package com.tierlist.tierlist.category.adapter.out.persistence;

import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryFavoriteJpaEntity.categoryFavoriteJpaEntity;
import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryJpaEntity.categoryJpaEntity;
import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.domain.model.CategoryFilter;
import com.tierlist.tierlist.category.application.port.in.service.dto.response.CategoryResponse;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryLoadRepository;
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
public class CategoryLoadRepositoryImpl implements CategoryLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

  private static OrderSpecifier<?>[] applyFilter(CategoryFilter filter) {
    return filter.equals(CategoryFilter.HOT) ?
        new OrderSpecifier<?>[]{
            categoryJpaEntity.favoriteCount.desc(),
            categoryJpaEntity.name.asc()
        } :
        new OrderSpecifier<?>[]{
            categoryJpaEntity.name.asc()
        };
  }

  private static BooleanExpression applyQuery(String query) {
    return Strings.isBlank(query) ? null : categoryJpaEntity.name.contains(query);
  }

  public Page<Category> loadFavoriteCategories(String email, Pageable pageable) {

    List<Category> categories = jpaQueryFactory
        .select(categoryJpaEntity)
        .from(memberJpaEntity)
        .join(categoryFavoriteJpaEntity)
        .on(memberJpaEntity.id.eq(categoryFavoriteJpaEntity.memberId))
        .join(categoryJpaEntity)
        .on(categoryFavoriteJpaEntity.categoryId.eq(categoryJpaEntity.id))
        .where(memberJpaEntity.email.eq(email))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch()
        .stream().map(CategoryJpaEntity::toCategory).toList();

    Long count = jpaQueryFactory
        .select(categoryJpaEntity.count())
        .from(memberJpaEntity)
        .join(categoryFavoriteJpaEntity)
        .on(memberJpaEntity.id.eq(categoryFavoriteJpaEntity.memberId))
        .join(categoryJpaEntity)
        .on(categoryFavoriteJpaEntity.categoryId.eq(categoryFavoriteJpaEntity.id))
        .where(memberJpaEntity.email.eq(email))
        .fetchOne();

    return new PageImpl<>(categories, pageable, Objects.isNull(count) ? 0 : count);
  }


  @Override
  public Page<CategoryResponse> loadCategories(String email, Pageable pageable, String query,
      CategoryFilter filter) {

    List<CategoryResponse> categoryResponses = jpaQueryFactory.select(
            Projections.constructor(CategoryResponse.class,
                categoryJpaEntity.id,
                categoryJpaEntity.name,
                memberJpaEntity.id.isNotNull().as("isFavorite"),
                categoryJpaEntity.favoriteCount
            ))
        .from(categoryJpaEntity)
        .leftJoin(categoryFavoriteJpaEntity)
        .on(categoryJpaEntity.id.eq(categoryFavoriteJpaEntity.categoryId))
        .leftJoin(memberJpaEntity)
        .on(categoryFavoriteJpaEntity.memberId.eq(memberJpaEntity.id),
            memberJpaEntity.email.eq(email)
        )
        .orderBy(applyFilter(filter))
        .where(applyQuery(query))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = jpaQueryFactory
        .select(categoryJpaEntity.count())
        .from(categoryJpaEntity)
        .where(applyQuery(query))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetchOne();

    return new PageImpl<>(categoryResponses, pageable, Objects.isNull(count) ? 0 : count);
  }

  @Override
  public CategoryResponse loadCategoryById(String viewerEmail, Long id) {
    return jpaQueryFactory.select(
            Projections.constructor(CategoryResponse.class,
                categoryJpaEntity.id,
                categoryJpaEntity.name,
                memberJpaEntity.id.isNotNull().as("isFavorite"),
                categoryJpaEntity.favoriteCount
            ))
        .from(categoryJpaEntity)
        .leftJoin(categoryFavoriteJpaEntity)
        .on(categoryJpaEntity.id.eq(categoryFavoriteJpaEntity.categoryId))
        .leftJoin(memberJpaEntity)
        .on(categoryFavoriteJpaEntity.memberId.eq(memberJpaEntity.id))
        .where(categoryJpaEntity.id.eq(id),
            memberJpaEntity.email.eq(viewerEmail).or(memberJpaEntity.isNull()))
        .fetchOne();
  }

}
