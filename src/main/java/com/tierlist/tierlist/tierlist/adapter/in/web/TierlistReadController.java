package com.tierlist.tierlist.tierlist.adapter.in.web;

import com.tierlist.tierlist.tierlist.application.domain.model.TierlistFilter;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistDetailResponse;
import com.tierlist.tierlist.tierlist.application.domain.service.dto.response.TierlistResponse;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistReadUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<List<TierlistResponse>> getTierlists(
      @AuthenticationPrincipal String email,
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TierlistFilter filter) {

    List<TierlistResponse> response = tierlistReadUseCase
        .getTierlists(email, pageCount, pageSize, query, filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/me/tierlist")
  public ResponseEntity<List<TierlistResponse>> getMyTierlists(
      @AuthenticationPrincipal String email,
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TierlistFilter filter) {

    List<TierlistResponse> response = tierlistReadUseCase
        .getMyTierlists(email, pageCount, pageSize, query, filter);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/category/{categoryId}/tierlist")
  public ResponseEntity<List<TierlistResponse>> getTierlistsOfCategory(
      @AuthenticationPrincipal String email,
      @PathVariable Long categoryId,
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TierlistFilter filter) {

    List<TierlistResponse> response = tierlistReadUseCase
        .getTierlistsOfCategory(email, categoryId, pageCount, pageSize, query, filter);
    return ResponseEntity.ok(response);

  }

  @GetMapping("/topic/{topicId}/tierlist")
  public ResponseEntity<List<TierlistResponse>> getTierlistsOfTopic(
      @AuthenticationPrincipal String email,
      @PathVariable Long topicId,
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TierlistFilter filter) {

    List<TierlistResponse> response = tierlistReadUseCase
        .getTierlistsOfTopic(email, topicId, pageCount, pageSize, query, filter);
    return ResponseEntity.ok(response);

  }

}
