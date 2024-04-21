package com.tierlist.tierlist.topic.application.port.in.service;

public interface TopicFavoriteUseCase {

  void toggleFavorite(String email, Long topicId);
}
