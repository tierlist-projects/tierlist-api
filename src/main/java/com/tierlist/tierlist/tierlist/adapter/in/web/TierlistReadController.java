package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/tierlist")
  public ResponseEntity<PageResponse<TierlistResponse>> getTierlists(
      @AuthenticationPrincipal String email,
      Pageable pageable,
      @RequestParam(required = false) String query,
      @RequestParam(defaultValue = "RECENT") TierlistFilter filter) {

    PageResponse<TierlistResponse> response = tierlistReadUseCase
        .getTierlists(email, pageable, query, filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/me/tierlist")
  public ResponseEntity<PageResponse<TierlistResponse>> getMyTierlists(
      @AuthenticationPrincipal String email,
      Pageable pageable,
      @RequestParam(required = false) String query,
      @RequestParam(defaultValue = "RECENT") TierlistFilter filter) {

    PageResponse<TierlistResponse> response = tierlistReadUseCase
        .getMyTierlists(email, pageable, query, filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/category/{categoryId}/tierlist")
  public ResponseEntity<PageResponse<TierlistResponse>> getTierlistsOfCategory(
      @AuthenticationPrincipal String email,
      @PathVariable Long categoryId,
      Pageable pageable,
      @RequestParam(required = false) String query,
      @RequestParam(defaultValue = "RECENT") TierlistFilter filter) {

    PageResponse<TierlistResponse> response = tierlistReadUseCase
        .getTierlistsOfCategory(email, categoryId, pageable, query, filter);
    return ResponseEntity.ok(response);

  }

  @GetMapping("/topic/{topicId}/tierlist")
  public ResponseEntity<PageResponse<TierlistResponse>> getTierlistsOfTopic(
      @AuthenticationPrincipal String email,
      @PathVariable Long topicId,
      Pageable pageable,
      @RequestParam(required = false) String query,
      @RequestParam(defaultValue = "RECENT") TierlistFilter filter) {

    PageResponse<TierlistResponse> response = tierlistReadUseCase
        .getTierlistsOfTopic(email, topicId, pageable, query, filter);
    return ResponseEntity.ok(response);

  }

}
