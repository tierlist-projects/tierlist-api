package com.tierlist.tierlist.topic.adapter.in.web;

import com.tierlist.tierlist.topic.application.port.in.service.TopicFavoriteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/topic")
@RestController
public class TopicFavoriteController {

  private final TopicFavoriteUseCase toggleFavoriteUseCase;

  @PatchMapping("/{topicId}/favorite/toggle")
  public ResponseEntity<Void> toggleTopicCategory(
      @AuthenticationPrincipal String email,
      @PathVariable Long topicId) {
    toggleFavoriteUseCase.toggleFavorite(email, topicId);
    return ResponseEntity.ok().build();
  }
}
