package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCommentCreateRequest;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistCommentController {

  private final TierlistCommentCreateUseCase tierlistCommentCreateUseCase;

  @PostMapping("/tierlist/{tierlistId}/comment")
  public ResponseEntity<Void> createTierlistComment(
      @AuthenticationPrincipal String email,
      @PathVariable Long tierlistId,
      @RequestBody @Valid TierlistCommentCreateRequest request) {
    Long commentId = tierlistCommentCreateUseCase.createComment(email, tierlistId,
        request.toCommand());
    return ResponseEntity
        .created(URI.create("/tierlist/" + tierlistId + "/comment/" + commentId))
        .build();
  }
}
