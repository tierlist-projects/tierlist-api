package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.topic.application.port.in.service.TopicFavoriteUseCase;
import org.springframework.stereotype.Service;

@Service
public class TopicFavoriteService implements TopicFavoriteUseCase {

  @Override
  public void toggleFavorite(String email, Long topicId) {

  }
}
