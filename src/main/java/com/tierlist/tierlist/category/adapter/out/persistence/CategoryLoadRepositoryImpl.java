package com.tierlist.tierlist.category.adapter.out.persistence;

import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryFavoriteJpaEntity.categoryFavoriteJpaEntity;
import static com.tierlist.tierlist.category.adapter.out.persistence.QCategoryJpaEntity.categoryJpaEntity;
import static com.tierlist.tierlist.member.adapter.out.persistence.QMemberJpaEntity.memberJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryLoadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class CategoryLoadRepositoryImpl implements CategoryLoadRepository {

  private final JPAQueryFactory jpaQueryFactory;

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

    return new PageImpl<>(categories, pageable, count);
  }

}
