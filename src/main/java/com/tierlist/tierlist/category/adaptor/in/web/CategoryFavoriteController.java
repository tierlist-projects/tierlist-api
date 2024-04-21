package com.tierlist.tierlist.category.adaptor.in.web;

import com.tierlist.tierlist.category.application.port.in.service.CategoryFavoriteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryFavoriteController {

  private final CategoryFavoriteUseCase categoryFavoriteUseCase;

  @PatchMapping("/{categoryId}/favorite/toggle")
  public ResponseEntity<Void> toggleCategoryFavorite(
      @AuthenticationPrincipal String email,
      @PathVariable Long categoryId) {
    categoryFavoriteUseCase.toggleFavorite(email, categoryId);
    return ResponseEntity.ok().build();
  }
}
