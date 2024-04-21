package com.tierlist.tierlist.category.application.port.in.service;

public interface CategoryFavoriteUseCase {

  void toggleFavorite(String email, Long categoryId);
}
