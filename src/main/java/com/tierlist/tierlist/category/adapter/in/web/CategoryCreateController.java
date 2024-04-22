package com.tierlist.tierlist.category.adapter.in.web;

import com.tierlist.tierlist.category.adapter.in.web.dto.request.CreateCategoryRequest;
import com.tierlist.tierlist.category.application.port.in.service.CategoryCreateUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<Void> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
    Long categoryId = categoryCreateUseCase.create(request.toCommand());
    return ResponseEntity.created(URI.create("/category/" + categoryId)).build();
  }

}
