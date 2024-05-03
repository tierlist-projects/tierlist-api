package com.tierlist.tierlist.tierlist.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.category.application.domain.model.Category;
import com.tierlist.tierlist.category.application.port.out.persistence.CategoryRepository;
import com.tierlist.tierlist.item.application.domain.model.Item;
import com.tierlist.tierlist.item.application.port.out.persistence.ItemRepository;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.category.FakeCategoryRepository;
import com.tierlist.tierlist.support.item.FakeItemRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import com.tierlist.tierlist.support.tierlist.FakeItemRankRepository;
import com.tierlist.tierlist.support.tierlist.FakeTierlistRepository;
import com.tierlist.tierlist.support.topic.FakeTopicRepository;
import com.tierlist.tierlist.tierlist.application.domain.exception.TierlistNotFoundException;
import com.tierlist.tierlist.tierlist.application.domain.model.ItemRank;
import com.tierlist.tierlist.tierlist.application.domain.model.Rank;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.command.ItemRankCommand;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistEditCommand;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.ItemRankRepository;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class TierlistEditServiceTest {

  private CategoryRepository categoryRepository;
  private TopicRepository topicRepository;
  private PasswordEncoder passwordEncoder;

  private TierlistRepository tierlistRepository;
  private ItemRankRepository itemRankRepository;
  private ItemRepository itemRepository;
  private MemberRepository memberRepository;

  private TierlistEditService sut;


  @BeforeEach
  void init() {
    categoryRepository = new FakeCategoryRepository();
    topicRepository = new FakeTopicRepository();
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    memberRepository = new FakeMemberRepository();
    tierlistRepository = new FakeTierlistRepository();
    itemRepository = new FakeItemRepository();
    itemRankRepository = new FakeItemRankRepository();

    sut = new TierlistEditService(memberRepository, tierlistRepository, itemRankRepository);
  }

  @Test
  void 티어리스트를_수정할_수_있다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .name("토픽")
        .categoryId(category.getId())
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .memberId(member.getId())
        .topicId(topic.getId())
        .title("티어리스트")
        .build());

    Item item1 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템1")
        .build());

    Item item2 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템2")
        .build());

    Item item3 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템3")
        .build());

    // when
    TierlistEditCommand command = TierlistEditCommand.builder()
        .title("티어리스트2")
        .content("내용")
        .sRanks(List.of(
            ItemRankCommand.builder()
                .itemId(item1.getId())
                .itemRankImage("item1-image")
                .build(),
            ItemRankCommand.builder()
                .itemId(item2.getId())
                .itemRankImage("item2-image")
                .build()
        ))
        .aRanks(List.of(
            ItemRankCommand.builder()
                .itemId(item3.getId())
                .itemRankImage("item3-image")
                .build()
        ))
        .bRanks(List.of())
        .cRanks(List.of())
        .dRanks(List.of())
        .fRanks(List.of())
        .noneRanks(List.of())
        .build();

    // when
    sut.editTierlist(member.getEmail(), tierlist.getId(), command);

    // then
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlist.getId());
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().getId()).isEqualTo(tierlist.getId());
    assertThat(tierlistOptional.get().getTitle()).isEqualTo(command.getTitle());
    assertThat(tierlistOptional.get().getContent()).isEqualTo(command.getContent());

    Optional<ItemRank> itemRankOptional1 =
        itemRankRepository.findByItemIdAndTierlistId(item1.getId(), tierlist.getId());
    assertThat(itemRankOptional1).isPresent();
    assertThat(itemRankOptional1.get().getRank()).isEqualTo(Rank.S);
    assertThat(itemRankOptional1.get().getOrder()).isZero();

    Optional<ItemRank> itemRankOptional2 =
        itemRankRepository.findByItemIdAndTierlistId(item2.getId(), tierlist.getId());
    assertThat(itemRankOptional2).isPresent();
    assertThat(itemRankOptional2.get().getRank()).isEqualTo(Rank.S);
    assertThat(itemRankOptional2.get().getOrder()).isOne();

    Optional<ItemRank> itemRankOptional3 =
        itemRankRepository.findByItemIdAndTierlistId(item3.getId(), tierlist.getId());
    assertThat(itemRankOptional3).isPresent();
    assertThat(itemRankOptional3.get().getRank()).isEqualTo(Rank.A);
    assertThat(itemRankOptional3.get().getOrder()).isZero();
  }

  @Test
  void 티어리스가_존재하지_않으면_티어리스트를_수정할_수_없다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .name("토픽")
        .categoryId(category.getId())
        .build());

    Item item1 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템1")
        .build());

    Item item2 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템2")
        .build());

    Item item3 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템3")
        .build());

    // when
    TierlistEditCommand command = TierlistEditCommand.builder()
        .title("티어리스트2")
        .content("내용")
        .sRanks(List.of(
            ItemRankCommand.builder()
                .itemId(item1.getId())
                .itemRankImage("item1-image")
                .build(),
            ItemRankCommand.builder()
                .itemId(item2.getId())
                .itemRankImage("item2-image")
                .build()
        ))
        .aRanks(List.of(
            ItemRankCommand.builder()
                .itemId(item3.getId())
                .itemRankImage("item3-image")
                .build()
        ))
        .bRanks(List.of())
        .cRanks(List.of())
        .dRanks(List.of())
        .fRanks(List.of())
        .noneRanks(List.of())
        .build();

    // when
    // then
    String memberEmail = member.getEmail();
    Assertions.assertThatThrownBy(() -> {
      sut.editTierlist(memberEmail, 1L, command);
    }).isInstanceOf(TierlistNotFoundException.class);
  }

  @Test
  void 티어리스트를_수정하면_기존_ItemRank_를_모두_삭제한다() {
    // given
    Member member = memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd!", passwordEncoder))
        .nickname("test")
        .build());

    Category category = categoryRepository.save(Category.builder()
        .name("카테고리")
        .build());

    Topic topic = topicRepository.save(Topic.builder()
        .name("토픽")
        .categoryId(category.getId())
        .build());

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .memberId(member.getId())
        .topicId(topic.getId())
        .title("티어리스트")
        .build());

    Item item1 = itemRepository.save(Item.builder()
        .categoryId(category.getId())
        .name("아이템1")
        .build());

    itemRankRepository.saveAll(List.of(ItemRank.builder()
        .rank(Rank.S)
        .order(0)
        .image("item1-image")
        .tierlistId(tierlist.getId())
        .itemId(item1.getId())
        .build()));

    // when
    TierlistEditCommand command = TierlistEditCommand.builder()
        .title("티어리스트2")
        .content("내용")
        .sRanks(List.of())
        .aRanks(List.of())
        .bRanks(List.of())
        .cRanks(List.of())
        .dRanks(List.of())
        .fRanks(List.of())
        .noneRanks(List.of())
        .build();

    // when
    sut.editTierlist(member.getEmail(), tierlist.getId(), command);

    // then
    Optional<Tierlist> tierlistOptional = tierlistRepository.findById(tierlist.getId());
    assertThat(tierlistOptional).isPresent();
    assertThat(tierlistOptional.get().getId()).isEqualTo(tierlist.getId());
    assertThat(tierlistOptional.get().getTitle()).isEqualTo(command.getTitle());
    assertThat(tierlistOptional.get().getContent()).isEqualTo(command.getContent());

    Optional<ItemRank> itemRankOptional1 =
        itemRankRepository.findByItemIdAndTierlistId(item1.getId(), tierlist.getId());
    assertThat(itemRankOptional1).isNotPresent();
  }
}