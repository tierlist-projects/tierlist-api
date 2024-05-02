package com.tierlist.tierlist.item.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.item.application.domain.exception.ItemNameDuplicationException;
import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.domain.model.command.ItemCreateCommand;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.item.FakeItemRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ItemCreateServiceTest {

  private CategoryRepository categoryRepository;
  private ItemRepository itemRepository;
  private ItemCreateService itemCreateService;

  @BeforeEach
  void init() {
    categoryRepository = new FakeCategoryRepository();
    itemRepository = new FakeItemRepository();
    itemCreateService = new ItemCreateService(itemRepository);
  }

  @Test
  void 아이템을_생성할_수_있다() {
    // given
    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    // when
    Long itemId = itemCreateService.create(ItemCreateCommand.builder()
        .categoryId(category.getId())
        .name("아이템")
        .build());

    // then
    Optional<Item> itemOptional = itemRepository.findById(itemId);
    assertThat(itemOptional).isPresent();
    assertThat(itemOptional.get().getCategoryId()).isEqualTo(category.getId());
    assertThat(itemOptional.get().getName()).isEqualTo("아이템");
  }

  @Test
  void 카테고리_내에서_중복된_이름의_아이템을_생성할_수_없다() {
    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    ItemCreateCommand command = ItemCreateCommand.builder()
        .categoryId(category.getId())
        .name("아이템")
        .build();

    itemCreateService.create(command);

    // when
    // then
    assertThatThrownBy(() -> {
      itemCreateService.create(command);
    }).isInstanceOf(ItemNameDuplicationException.class);
  }

  @Test
  void 다른_카테고리에서는_중복된_이름의_아이템을_생성할_수_있다() {
    Category category1 = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    Category category2 = categoryRepository.save(Category.builder()
        .name("카테고리2")
        .build());

    itemCreateService.create(ItemCreateCommand.builder()
        .categoryId(category1.getId())
        .name("아이템")
        .build());

    // when
    Long itemId = itemCreateService.create(ItemCreateCommand.builder()
        .categoryId(category2.getId())
        .name("아이템")
        .build());

    // then
    Optional<Item> itemOptional = itemRepository.findById(itemId);
    assertThat(itemOptional).isPresent();
    assertThat(itemOptional.get().getCategoryId()).isEqualTo(category2.getId());
    assertThat(itemOptional.get().getName()).isEqualTo("아이템");
  }

}