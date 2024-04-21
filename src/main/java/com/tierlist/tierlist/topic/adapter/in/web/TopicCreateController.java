package com.tierlist.tierlist.topic.adapter.in.web;

import com.tierlist.tierlist.topic.adapter.in.web.dto.request.TopicCreateRequest;
import com.tierlist.tierlist.topic.application.port.in.service.TopicCreateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/topic")
@RestController
public class TopicCreateController {

  private final TopicCreateUseCase topicCreateUseCase;

  @PostMapping
  public ResponseEntity<Void> createTopic(@RequestBody @Valid TopicCreateRequest request) {
    Long topicId = topicCreateUseCase.create(request.toCommand());
    return ResponseEntity.created(
            URI.create("/category/" + request.getCategoryId() + "/topic/" + topicId))
        .build();
  }
}
