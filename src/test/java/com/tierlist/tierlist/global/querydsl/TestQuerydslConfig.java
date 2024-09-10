package com.tierlist.tierlist.global.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLTemplates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQuerydslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public SQLTemplates sqlTemplates() {
    return H2Templates.builder().build();
  }
}
