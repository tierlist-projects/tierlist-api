package com.tierlist.tierlist.item.application.port.in.service;

import com.tierlist.tierlist.item.application.domain.model.command.ItemCreateCommand;

public interface ItemCreateUseCase {

  Long create(ItemCreateCommand command);
}
