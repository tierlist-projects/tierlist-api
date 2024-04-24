package com.tierlist.tierlist.category.adapter.out.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {

  boolean existsByName(String name);

}
