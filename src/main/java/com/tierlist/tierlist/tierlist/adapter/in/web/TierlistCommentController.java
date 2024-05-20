package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.request.TierlistCommentCreateRequest;
import com.tierlist.tierlist.tierlist.adapter.in.web.dto.response.CommentCreateResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistCommentResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentCreateUseCase;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCommentReadUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TierlistCommentController {

  private final TierlistCommentCreateUseCase tierlistCommentCreateUseCase;
  private final TierlistCommentReadUseCase tierlistCommentReadUseCase;

  @PostMapping("/tierlist/{tierlistId}/comment")
  public ResponseEntity<CommentCreateResponse> createTierlistComment(
      @AuthenticationPrincipal String email,
      @PathVariable Long tierlistId,
      @RequestBody @Valid TierlistCommentCreateRequest request) {
    Long commentId = tierlistCommentCreateUseCase.createComment(email, tierlistId,
        request.toCommand());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(CommentCreateResponse.builder()
            .commentId(commentId)
            .build());
  }

  @GetMapping("/tierlist/{tierlistId}/comment")
  public ResponseEntity<PageResponse<TierlistCommentResponse>> getTierlistComments(
      @AuthenticationPrincipal String email,
      Pageable pageable,
      @PathVariable Long tierlistId) {
    PageResponse<TierlistCommentResponse> tierlistComments = tierlistCommentReadUseCase.getTierlistComments(
        email, tierlistId, pageable);
    return ResponseEntity.ok(tierlistComments);
  }
}
