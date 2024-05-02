package com.tierlist.tierlist.item.application.domain.service;

import com.tierlist.tierlist.item.application.domain.exception.ItemNameDuplicationException;
import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.domain.model.command.ItemCreateCommand;
import com.tierlist.tierlist.item.application.port.in.service.ItemCreateUseCase;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemCreateService implements ItemCreateUseCase {

  private final ItemRepository itemRepository;

  @Transactional
  @Override
  public Long create(ItemCreateCommand command) {
    if (itemRepository.existsByNameAndCategoryId(command.getName(), command.getCategoryId())) {
      throw new ItemNameDuplicationException();
    }

    Item item = itemRepository.save(command.toItem());

    return item.getId();
  }
}
