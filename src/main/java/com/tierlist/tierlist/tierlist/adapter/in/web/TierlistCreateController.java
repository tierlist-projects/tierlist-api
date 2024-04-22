package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCreateRequest;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCreateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/tierlist")
@RestController
public class TierlistCreateController {

  private final TierlistCreateUseCase tierlistCreateUseCase;

  @PostMapping
  public ResponseEntity<Void> createTierlist(@RequestBody @Valid TierlistCreateRequest request) {
    Long tierlistId = tierlistCreateUseCase.create(request.toCommand());
    return ResponseEntity.created(URI.create("/tierlist/" + tierlistId)).build();
  }
}
