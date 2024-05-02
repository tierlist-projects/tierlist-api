package com.tierlist.tierlist.support.item;

import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeItemRepository implements ItemRepository {

  private List<Item> data = new ArrayList<>();
  private Long autoGeneratedId = 0L;

  @Override
  public Item save(Item item) {
    Long id = item.getId();
    if (id == null || id == 0) {
      Item newItem = Item.builder()
          .id(++autoGeneratedId)
          .name(item.getName())
          .categoryId(item.getCategoryId())
          .build();
      data.add(newItem);
      return newItem;
    }

    data.removeIf(i -> i.getId().equals(id));
    data.add(item);
    return item;
  }

  @Override
  public Optional<Item> findById(Long id) {
    return data.stream()
        .filter(item -> item.getId().equals(id))
        .findFirst();
  }

  @Override
  public boolean existsByNameAndCategoryId(String name, Long categoryId) {
    return data.stream()
        .anyMatch(item -> item.getName().equals(name) &&
            item.getCategoryId().equals(categoryId));
  }
}
