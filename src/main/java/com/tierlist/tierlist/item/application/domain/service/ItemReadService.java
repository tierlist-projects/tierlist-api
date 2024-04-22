package com.tierlist.tierlist.item.application.domain.service;

import com.tierlist.tierlist.item.application.port.in.service.ItemReadUseCase;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemReadService implements ItemReadUseCase {

  @Override
  public List<ItemResponse> getItems(Long categoryId, String query, int pageCount, int pageSize) {
    return List.of();
  }
}
