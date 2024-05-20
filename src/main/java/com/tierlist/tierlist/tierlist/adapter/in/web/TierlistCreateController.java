package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCreateRequest;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.response.TierlistCreateResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<TierlistCreateResponse> createTierlist(
      @AuthenticationPrincipal String email,
      @RequestBody @Valid TierlistCreateRequest request) {
    Long tierlistId = tierlistCreateUseCase.create(email, request.toCommand());
    return ResponseEntity.status(HttpStatus.CREATED).body(TierlistCreateResponse.builder()
        .tierlistId(tierlistId)
        .build());
  }
}
