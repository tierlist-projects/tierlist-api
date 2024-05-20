package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.domain.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class TierlistCreateServiceTest {

  private TopicRepository topicRepository;
  private TierlistRepository tierlistRepository;
  private MemberRepository memberRepository;

  private CategoryRepository categoryRepository;
  private PasswordEncoder passwordEncoder;

  private TierlistCreateService sut;


  @BeforeEach
  void init() {
    topicRepository = new FakeTopicRepository();
    memberRepository = new FakeMemberRepository();
    tierlistRepository = new FakeTierlistRepository();

    categoryRepository = new FakeCategoryRepository();
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    sut = new TierlistCreateService(
        tierlistRepository,
        topicRepository,
        memberRepository
    );
  }

  @Test
  void 티어리스트를_생성할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("test")
        .password(Password.fromRawPassword("123qweasd", passwordEncoder))
        .profileImage("profile-image")
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .categoryId(category.getId())
        .name("토픽")
        .build());

    // when
    Long tierlistId = sut.create(member.getEmail(), TierlistCreateCommand.builder()
        .title("티어리스트")
        .topicId(topic.getId())
        .build());

    // then
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlistId);
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().getTitle()).isEqualTo("티어리스트");
  }

  @Test
  void 멤버가_존재하지_않으면_티어리스트를_생성할_수_없다() {
    // given
    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .categoryId(category.getId())
        .name("토픽")
        .build());

    // when
    // then
    TierlistCreateCommand command = TierlistCreateCommand.builder()
        .title("티어리스트")
        .topicId(topic.getId())
        .build();

    assertThatThrownBy(() -> {
      sut.create("test@test.com", command);
    }).isInstanceOf(MemberNotFoundException.class);
  }

  @Test
  void 토픽이_존재하지_않으면_티어리스트를_생성할_수_없다() {
    // given

    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .nickname("test")
        .password(Password.fromRawPassword("123qweasd", passwordEncoder))
        .profileImage("profile-image")
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    // when
    // then
    TierlistCreateCommand command = TierlistCreateCommand.builder()
        .title("티어리스트")
        .topicId(1L)
        .build();

    assertThatThrownBy(() -> {
      sut.create("test@test.com", command);
    }).isInstanceOf(TopicNotFoundException.class);
  }
}