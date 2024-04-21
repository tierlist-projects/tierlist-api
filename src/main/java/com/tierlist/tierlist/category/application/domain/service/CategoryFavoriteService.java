package com.tierlist.tierlist.category.application.domain.service;

import com.tierlist.tierlist.category.application.port.in.service.CategoryFavoriteUseCase;
import org.springframework.stereotype.Service;

@Service
public class CategoryFavoriteService implements CategoryFavoriteUseCase {

  @Override
  public void toggleFavorite(String email, Long categoryId) {

  }
}
