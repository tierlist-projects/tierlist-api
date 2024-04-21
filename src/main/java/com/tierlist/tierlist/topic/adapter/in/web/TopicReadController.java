package com.tierlist.tierlist.topic.adapter.in.web;

import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
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
public class TopicReadController {

  private final TopicReadUseCase topicReadUseCase;

  @GetMapping("/topic")
  public ResponseEntity<List<TopicResponse>> getTopics(
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TopicFilter filter) {
    return ResponseEntity.ok(topicReadUseCase.getTopics(null, pageCount, pageSize, query, filter));
  }

  @GetMapping("/category/{categoryId}/topic")
  public ResponseEntity<List<TopicResponse>> getTopicsOfCategories(
      @PathVariable Long categoryId,
      @RequestParam int pageCount,
      @RequestParam int pageSize,
      @RequestParam String query,
      @RequestParam TopicFilter filter) {
    return ResponseEntity.ok(
        topicReadUseCase.getTopics(categoryId, pageCount, pageSize, query, filter));
  }

  @GetMapping("topic/favorite")
  public ResponseEntity<List<TopicResponse>> getFavoriteCategories(
      @AuthenticationPrincipal String email,
      @RequestParam int pageCount,
      @RequestParam int pageSize) {
    return ResponseEntity.ok(topicReadUseCase.getFavoriteTopics(email, pageCount, pageSize));
  }

}
