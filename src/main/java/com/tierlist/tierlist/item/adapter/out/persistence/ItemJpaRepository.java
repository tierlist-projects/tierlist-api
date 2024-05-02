package com.tierlist.tierlist.item.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, Long> {

  boolean existsByNameAndCategoryId(String name, Long categoryId);
}
