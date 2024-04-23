package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistReadController {

  private final TierlistReadUseCase tierlistReadUseCase;

  @GetMapping("/tierlist/{tierlistId}")
  public ResponseEntity<TierlistDetailResponse> getTierlist(@AuthenticationPrincipal String email,
      @PathVariable Long tierlistId) {
    TierlistDetailResponse response = tierlistReadUseCase.getTierlist(email, tierlistId);
    return ResponseEntity.ok(response);
  }
}
