package com.tierlist.tierlist.topic.adapter.out.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicJpaRepository extends JpaRepository<TopicJpaEntity, Long> {

  boolean existsByName(String name);
}
