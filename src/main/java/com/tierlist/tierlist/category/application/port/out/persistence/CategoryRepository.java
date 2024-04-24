package com.tierlist.tierlist.category.application.port.out.persistence;

import com.tierlist.tierlist.category.application.domain.model.Category;
import java.util.Optional;

public interface CategoryRepository {

  boolean existsByName(String name);

  Category save(Category category);

  Optional<Category> findById(Long id);

}
