package com.tierlist.tierlist.item.application.port.out.persistence;

import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemLoadRepository {

  Page<ItemResponse> getItems(Long categoryId, Pageable pageable, String query);
}
