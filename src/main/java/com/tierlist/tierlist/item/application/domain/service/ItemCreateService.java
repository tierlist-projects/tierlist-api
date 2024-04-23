package com.tierlist.tierlist.item.application.domain.service;

import com.tierlist.tierlist.item.application.domain.model.command.ItemCreateCommand;
import com.tierlist.tierlist.item.application.port.in.service.ItemCreateUseCase;
import org.springframework.stereotype.Service;

@Service
public class ItemCreateService implements ItemCreateUseCase {

  @Override
  public Long create(ItemCreateCommand command) {
    return 0L;
  }
}
