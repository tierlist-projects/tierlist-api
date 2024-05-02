package com.tierlist.tierlist.item.application.domain.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.item.application.port.in.service.ItemReadUseCase;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemLoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemReadService implements ItemReadUseCase {

  private final ItemLoadRepository itemLoadRepository;

  @Override
  public PageResponse<ItemResponse> getItems(Long categoryId, Pageable pageable, String query) {
    return PageResponse.fromPage(itemLoadRepository.getItems(categoryId, pageable, query));
  }
}
