package com.tierlist.tierlist.topic.application.port.out.persistence;

import com.tierlist.tierlist.topic.application.domain.model.TopicFilter;
import com.tierlist.tierlist.topic.application.port.in.service.dto.response.TopicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicLoadRepository {


  Page<TopicResponse> loadFavoriteTopics(String email, Pageable pageable);

  Page<TopicResponse> loadTopics(String email, Long categoryId, Pageable pageable, String query,
      TopicFilter filter);
}
