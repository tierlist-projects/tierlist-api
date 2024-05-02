package com.tierlist.tierlist.item.application.port.in.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import org.springframework.data.domain.Pageable;

public interface ItemReadUseCase {

  PageResponse<ItemResponse> getItems(Long categoryId, Pageable pageable, String query);

}
