package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCommentCreateRequest;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentReadUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistCommentController {

  private final TierlistCommentCreateUseCase tierlistCommentCreateUseCase;
  private final TierlistCommentReadUseCase tierlistCommentReadUseCase;

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

  @GetMapping("/tierlist/{tierlistId}/comment")
  public ResponseEntity<List<TierlistCommentResponse>> getTierlistComments(
      @AuthenticationPrincipal String email,
      @PathVariable Long tierlistId,
      @RequestParam int pageCount,
      @RequestParam int pageSize) {
    List<TierlistCommentResponse> tierlistComments = tierlistCommentReadUseCase.getTierlistComments(
        email, tierlistId, pageCount, pageSize);
    return ResponseEntity.ok(tierlistComments);
  }
}
