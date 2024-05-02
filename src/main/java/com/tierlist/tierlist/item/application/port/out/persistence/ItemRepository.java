package com.tierlist.tierlist.item.application.port.out.persistence;

import com.tierlist.tierlist.item.application.domain.model.Item;
import java.util.Optional;

public interface ItemRepository {

  Item save(Item item);

  Optional<Item> findById(Long id);

  boolean existsByNameAndCategoryId(String name, Long categoryId);
}
