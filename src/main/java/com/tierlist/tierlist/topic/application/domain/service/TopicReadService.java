package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicLoadRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TopicReadService implements TopicReadUseCase {

  private final TopicLoadRepository topicLoadRepository;

  @Transactional(readOnly = true)
  @Override
  public PageResponse<TopicResponse> getTopics(String email, Long categoryId, Pageable pageable,
      String query,
      TopicFilter filter) {
    return PageResponse.fromPage(
        topicLoadRepository.loadTopics(email, categoryId, pageable, query, filter));
  }

  @Transactional(readOnly = true)
  @Override
  public PageResponse<TopicResponse> getFavoriteTopics(String email, Pageable pageable) {
    Page<TopicResponse> topics = topicLoadRepository.loadFavoriteTopics(email, pageable);
    return PageResponse.fromPage(topics);
  }

  @Transactional(readOnly = true)
  @Override
  public TopicResponse getTopic(String email, Long topicId) {
    TopicResponse topicResponse = topicLoadRepository.loadTopic(email, topicId);
    if (Objects.isNull(topicResponse)) {
      throw new TopicNotFoundException();
    }
    return topicResponse;
  }
}
