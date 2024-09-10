package com.tierlist.tierlist.global.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class QuerydslConfig {

  private final EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public SQLTemplates sqlTemplates() {
    return MySQLTemplates.builder().build();
  }

}
