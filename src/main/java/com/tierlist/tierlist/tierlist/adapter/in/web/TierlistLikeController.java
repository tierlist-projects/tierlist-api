package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistLikeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistLikeController {

  private final TierlistLikeUseCase tierlistLikeUseCase;

  @PatchMapping("/tierlist/{tierlistId}/like/toggle")
  public ResponseEntity<Void> toggleLike(
      @AuthenticationPrincipal String email,
      @PathVariable Long tierlistId) {
    tierlistLikeUseCase.toggleLike(email, tierlistId);
    return ResponseEntity.ok().build();
  }

}
