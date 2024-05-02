package com.tierlist.tierlist.item.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.global.support.repository.RepositoryTest;
import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.port.in.service.dto.response.ItemResponse;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@RepositoryTest
class ItemLoadRepositoryImplTest {

  @Autowired
  private ItemLoadRepositoryImpl itemLoadRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void 카테고리_내_아이템_목록을_조회할_수_있다() {
    // given
    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Item item12 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템12")
        .build());

    Item item11 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템11")
        .build());

    Item item21 = itemRepository.save(Item.builder()
        .categoryId(category2.getId())
        .name("아이템21")
        .build());

    // when
    Page<ItemResponse> items = itemLoadRepository.getItems(category1.getId(), PageRequest.of(0, 3),
        null);

    // then
    assertThat(items.getContent()).hasSize(2);
    assertThat(items.getContent().get(0).getId()).isEqualTo(item11.getId());
    assertThat(items.getContent().get(1).getId()).isEqualTo(item12.getId());
  }

  @Test
  void 카테고리_내_아이템_목록을_검색어로_조회할_수_있다() {
    // given
    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    Item item12 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템12")
        .build());

    Item item11 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템11")
        .build());

    Item item31 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템31")
        .build());

    Item item32 = itemRepository.save(Item.builder()
        .categoryId(category1.getId())
        .name("아이템32")
        .build());

    Item item21 = itemRepository.save(Item.builder()
        .categoryId(category2.getId())
        .name("아이템21")
        .build());

    // when
    Page<ItemResponse> items = itemLoadRepository.getItems(category1.getId(), PageRequest.of(0, 3),
        "3");

    // then
    assertThat(items.getContent()).hasSize(2);
    assertThat(items.getContent().get(0).getId()).isEqualTo(item31.getId());
    assertThat(items.getContent().get(1).getId()).isEqualTo(item32.getId());
  }
}