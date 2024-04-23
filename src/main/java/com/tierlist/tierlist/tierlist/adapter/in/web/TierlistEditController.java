package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistEditRequest;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistEditUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistEditController {

  private final TierlistEditUseCase tierlistEditUseCase;

  @PutMapping("/tierlist/{tierlistId}")
  public ResponseEntity<Void> editTierlist(
      @AuthenticationPrincipal String email,
      @PathVariable("tierlistId") Long tierlistId,
      @RequestBody @Valid TierlistEditRequest request) {
    tierlistEditUseCase.editTierlist(email, tierlistId, request.toCommand());
    return ResponseEntity.ok().build();
  }

}
