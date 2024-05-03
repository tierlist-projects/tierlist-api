package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistAuthorizationException;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class TierlistPublishServiceTest {

  private TierlistPublishService sut;

  private TierlistRepository tierlistRepository;
  private MemberRepository memberRepository;

  private CategoryRepository categoryRepository;
  private TopicRepository topicRepository;
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    tierlistRepository = new FakeTierlistRepository();
    memberRepository = new FakeMemberRepository();

    categoryRepository = new FakeCategoryRepository();
    topicRepository = new FakeTopicRepository();

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    sut = new TierlistPublishService(memberRepository, tierlistRepository);
  }

  @Test
  void 티어리스트_발행_상태를_변경할_수_있다() {
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

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .title("티어리스트")
        .memberId(member.getId())
        .topicId(topic.getId())
        .build());

    // when
    sut.togglePublish(member.getEmail(), tierlist.getId());

    // then
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlist.getId());
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().isPublished()).isTrue();
    assertThat(tierlistOptional.get().getTitle()).isEqualTo("티어리스트");
  }

  @Test
  void 티어리스트_작성자가_아니면_티어리스트_발행_상태를_변경할_수_없다() {
    // given
    Member member1 = memberRepository.save(Member.builder()
        .email("test1@test.com")
        .nickname("test1")
        .password(Password.fromRawPassword("123qweasd", passwordEncoder))
        .profileImage("profile-image")
        .build());

    Member member2 = memberRepository.save(Member.builder()
        .email("test2@test.com")
        .nickname("test2")
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

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .title("티어리스트")
        .memberId(member1.getId())
        .topicId(topic.getId())
        .build());

    // when
    // then
    String memberEmail = member2.getEmail();
    Long tierlistId = tierlist.getId();

    assertThatThrownBy(() -> {
      sut.togglePublish(memberEmail, tierlistId);
    }).isInstanceOf(TierlistAuthorizationException.class);
  }
}