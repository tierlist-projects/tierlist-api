package com.tierlist.tierlist.topic.application.port.in.service;

import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import java.util.List;

public interface TopicReadUseCase {

  List<TopicResponse> getTopics(Long categoryId, int pageCount, int pageSize, String query,
      TopicFilter filter);

  List<TopicResponse> getFavoriteTopics(String email, int pageCount, int pageSize);

}
