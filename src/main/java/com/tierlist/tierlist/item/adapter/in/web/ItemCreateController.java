package com.tierlist.tierlist.item.adapter.in.web;

import com.tierlist.tierlist.item.adapter.in.web.dto.request.ItemCreateRequest;
import com.tierlist.tierlist.item.application.port.in.service.ItemCreateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ItemCreateController {

  private final ItemCreateUseCase itemCreateUseCase;

  @PostMapping("/item")
  public ResponseEntity<Void> createItem(@RequestBody @Valid ItemCreateRequest request) {
    Long itemId = itemCreateUseCase.create(request.toCommand());
    return ResponseEntity.created(URI.create("/item/" + itemId)).build();
  }
}
