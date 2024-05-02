package com.tierlist.tierlist.item.adapter.out.persistence;

import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ItemRepositoryImpl implements ItemRepository {

  private final ItemJpaRepository itemJpaRepository;

  @Override
  public Item save(Item item) {
    return itemJpaRepository.save(ItemJpaEntity.from(item)).toItem();
  }

  @Override
  public Optional<Item> findById(Long id) {
    return itemJpaRepository.findById(id).map(ItemJpaEntity::toItem);
  }

  @Override
  public boolean existsByNameAndCategoryId(String name, Long categoryId) {
    return itemJpaRepository.existsByNameAndCategoryId(name, categoryId);
  }
}
