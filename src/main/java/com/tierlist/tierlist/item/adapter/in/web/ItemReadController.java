package com.tierlist.tierlist.item.adapter.in.web;

import com.tierlist.tierlist.item.application.port.in.service.ItemReadUseCase;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<List<ItemResponse>> getItems(
      @PathVariable Long categoryId,
      @RequestParam String query,
      @RequestParam int pageCount,
      @RequestParam int pageSize) {
    List<ItemResponse> response = itemReadUseCase.getItems(categoryId, query, pageCount, pageSize);
    return ResponseEntity.ok(response);
  }
}
