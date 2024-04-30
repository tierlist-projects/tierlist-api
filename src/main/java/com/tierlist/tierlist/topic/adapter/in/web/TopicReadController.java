package com.tierlist.tierlist.topic.adapter.in.web;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
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
public class TopicReadController {

  private final TopicReadUseCase topicReadUseCase;

  @GetMapping("/topic")
  public ResponseEntity<PageResponse<TopicResponse>> getTopics(
      @AuthenticationPrincipal String email,
      Pageable pageable,
      @RequestParam String query,
      @RequestParam TopicFilter filter) {
    return ResponseEntity.ok(topicReadUseCase.getTopics(email, null, pageable, query, filter));
  }

  @GetMapping("/category/{categoryId}/topic")
  public ResponseEntity<PageResponse<TopicResponse>> getTopicsOfCategories(
      @PathVariable Long categoryId,
      @AuthenticationPrincipal String email,
      Pageable pageable,
      @RequestParam String query,
      @RequestParam TopicFilter filter) {
    return ResponseEntity.ok(
        topicReadUseCase.getTopics(email, categoryId, pageable, query, filter));
  }

  @GetMapping("topic/favorite")
  public ResponseEntity<PageResponse<TopicResponse>> getFavoriteCategories(
      @AuthenticationPrincipal String email,
      Pageable pageable) {
    return ResponseEntity.ok(topicReadUseCase.getFavoriteTopics(email, pageable));
  }

}
