package com.tierlist.tierlist.category.adapter.in.web;

import com.tierlist.tierlist.category.adapter.in.web.dto.request.CreateCategoryRequest;
import com.tierlist.tierlist.category.adapter.in.web.dto.response.CategoryCreateResponse;
import com.tierlist.tierlist.category.application.port.in.service.CategoryCreateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryCreateController {

  private final CategoryCreateUseCase categoryCreateUseCase;

  @PostMapping
  public ResponseEntity<CategoryCreateResponse> createCategory(
      @RequestBody @Valid CreateCategoryRequest request) {
    Long categoryId = categoryCreateUseCase.create(request.toCommand());
    return ResponseEntity.status(HttpStatus.CREATED).body(CategoryCreateResponse.builder()
        .categoryId(categoryId)
        .build());
  }

}
