package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.TopicReadUseCase;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TopicReadService implements TopicReadUseCase {

  @Override
  public List<TopicResponse> getTopics(Long categoryId, int pageCount, int pageSize, String query,
      TopicFilter filter) {
    return List.of();
  }

  @Override
  public List<TopicResponse> getFavoriteTopics(String email, int pageCount, int pageSize) {
    return List.of();
  }
}
