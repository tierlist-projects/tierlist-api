package com.tierlist.tierlist.topic.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.exception.CategoryNotFoundException;
import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.topic.application.domain.exception.TopicDuplicationException;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.domain.model.command.TopicCreateCommand;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TopicCreateServiceTest {

  private TopicCreateService topicCreateService;
  private TopicRepository topicRepository;
  private CategoryRepository categoryRepository;

  @BeforeEach
  void init() {
    categoryRepository = new FakeCategoryRepository();
    topicRepository = new FakeTopicRepository();
    topicCreateService = new TopicCreateService(categoryRepository, topicRepository);
  }

  @Test
  void 토픽을_생성할_수_있다() {
    // given
    Category category = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    // when
    Long topicId = topicCreateService.create(TopicCreateCommand.builder()
        .categoryId(category.getId())
        .name("토픽1")
        .build());

    // then
    Optional<Topic> topicOptional = topicRepository.findById(topicId);

    assertThat(topicOptional).isPresent();
    assertThat(topicOptional.get().getCategoryId()).isEqualTo(category.getId());
    assertThat(topicOptional.get().getName()).isEqualTo("토픽1");
  }

  @Test
  void 카테고리가_존재하지_않는_토픽을_생성할_수_없다() {
    // given
    TopicCreateCommand command = TopicCreateCommand.builder()
        .categoryId(1L)
        .name("토픽1")
        .build();
    // when
    // then
    assertThatThrownBy(() -> {
      topicCreateService.create(command);
    }).isInstanceOf(CategoryNotFoundException.class);
  }

  @Test
  void 중복된_이름의_토픽을_생성할_수_없다() {
    // given
    Category category = categoryRepository.save(Category.builder()
        .name("카테고리1")
        .build());

    topicRepository.save(Topic.builder()
        .categoryId(1L)
        .name("토픽1")
        .build());

    TopicCreateCommand command = TopicCreateCommand.builder()
        .categoryId(1L)
        .name("토픽1")
        .build();

    // when
    // then
    assertThatThrownBy(() -> {
      topicCreateService.create(command);
    }).isInstanceOf(TopicDuplicationException.class);
  }

}