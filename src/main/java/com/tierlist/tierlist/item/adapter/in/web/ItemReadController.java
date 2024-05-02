package com.tierlist.tierlist.item.adapter.in.web;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.item.application.port.in.service.ItemReadUseCase;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemReadController {

  private final ItemReadUseCase itemReadUseCase;

  @GetMapping("/category/{categoryId}/item")
  public ResponseEntity<PageResponse<ItemResponse>> getItems(
      @PathVariable Long categoryId,
      Pageable pageable,
      @RequestParam(required = false) String query) {
    PageResponse<ItemResponse> response = itemReadUseCase.getItems(categoryId, pageable, query);
    return ResponseEntity.ok(response);
  }
}
