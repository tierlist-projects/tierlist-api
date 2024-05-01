package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicLoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TopicReadService implements TopicReadUseCase {

  private final TopicLoadRepository topicLoadRepository;

  @Override
  public PageResponse<TopicResponse> getTopics(String email, Long categoryId, Pageable pageable,
      String query,
      TopicFilter filter) {
    return PageResponse.fromPage(
        topicLoadRepository.loadTopics(email, categoryId, pageable, query, filter));
  }

  @Override
  public PageResponse<TopicResponse> getFavoriteTopics(String email, Pageable pageable) {
    Page<TopicResponse> topics = topicLoadRepository.loadFavoriteTopics(email, pageable);
    return PageResponse.fromPage(topics);
  }
}
