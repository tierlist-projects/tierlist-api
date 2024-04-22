package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistPublishUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistPublishController {

  private final TierlistPublishUseCase tierlistPublishUseCase;

  @PatchMapping("/tierlist/{tierlistId}/publish/toggle")
  public ResponseEntity<Void> toggleLike(
      @AuthenticationPrincipal String email,
      @PathVariable Long tierlistId) {
    tierlistPublishUseCase.togglePublish(email, tierlistId);
    return ResponseEntity.ok().build();
  }
}
