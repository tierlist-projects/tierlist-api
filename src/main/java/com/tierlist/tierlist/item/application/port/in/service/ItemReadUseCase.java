package com.tierlist.tierlist.item.application.port.in.service;

import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import java.util.List;

public interface ItemReadUseCase {

  List<ItemResponse> getItems(Long categoryId, String query, int pageCount, int pageSize);

}
