package com.tierlist.tierlist.topic.application.port.in.service;

import com.tierlist.tierlist.global.common.response.PageResponse;
import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import org.springframework.data.domain.Pageable;

public interface TopicReadUseCase {

  PageResponse<TopicResponse> getTopics(String email, Long categoryId, Pageable pageable,
      String query,
      TopicFilter filter);

  PageResponse<TopicResponse> getFavoriteTopics(String email, Pageable pageable);

  TopicResponse getTopic(String email, Long topicId);
}
