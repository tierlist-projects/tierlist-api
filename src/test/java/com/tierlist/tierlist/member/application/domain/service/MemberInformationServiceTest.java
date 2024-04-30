package com.tierlist.tierlist.member.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberNicknameCommand;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberPasswordCommand;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberProfileImageCommand;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.support.member.FakeMemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberInformationServiceTest {

  private MemberRepository memberRepository;
  private MemberInformationService memberInformationService;
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    memberRepository = new FakeMemberRepository();
    memberInformationService = new MemberInformationService(memberRepository, passwordEncoder);
  }

  @Test
  void 프로필_이미지를_변경할_수_있다() {
    // given
    memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("123qweasd", passwordEncoder))
        .nickname("test")
        .build());

    // when
    memberInformationService.changeMemberProfileImage("test@test.com",
        ChangeMemberProfileImageCommand.builder()
            .profileImageName("new-profile-image")
            .build());

    // then
    Optional<Member> memberOptional = memberRepository.findByEmail("test@test.com");

    assertThat(memberOptional).isPresent();
    assertThat(memberOptional.get().getEmail()).isEqualTo("test@test.com");
    assertThat(memberOptional.get().getProfileImage()).isEqualTo("new-profile-image");
  }

  @Test
  void 비밀번호를_변경할_수_있다() {
    // given
    memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("originalPassword", passwordEncoder))
        .nickname("test")
        .build());

    // when
    memberInformationService.changeMemberPassword("test@test.com",
        ChangeMemberPasswordCommand.builder()
            .password("originalPassword")
            .newPassword("newPassword")
            .build());

    // then
    Optional<Member> memberOptional = memberRepository.findByEmail("test@test.com");

    assertThat(memberOptional).isPresent();
    assertThat(memberOptional.get().getPassword().matches("newPassword", passwordEncoder)).isTrue();
  }

  @Test
  void 닉네임을_변경할_수_있다() {
    // given
    memberRepository.save(Member.builder()
        .email("test@test.com")
        .password(Password.fromRawPassword("originalPassword", passwordEncoder))
        .nickname("test")
        .build());

    // when
    memberInformationService.changeMemberNickname("test@test.com",
        ChangeMemberNicknameCommand.builder()
            .nickname("newNick")
            .build());

    // then
    Optional<Member> memberOptional = memberRepository.findByEmail("test@test.com");

    assertThat(memberOptional).isPresent();
    assertThat(memberOptional.get().getNickname()).isEqualTo("newNick");
  }

  @Test
  void 중복된_닉네임으로_닉네임을_변경할_수_없다() {
    // given
    memberRepository.save(Member.builder()
        .email("test2@test.com")
        .password(Password.fromRawPassword("originalPassword", passwordEncoder))
        .nickname("test1")
        .build());

    memberRepository.save(Member.builder()
        .email("test2@test.com")
        .password(Password.fromRawPassword("originalPassword", passwordEncoder))
        .nickname("test2")
        .build());

    // when
    // then
    assertThatThrownBy(() -> {
      memberInformationService.changeMemberNickname("test2@test.com",
          ChangeMemberNicknameCommand.builder()
              .nickname("test1")
              .build());
    }).isInstanceOf(NicknameDuplicationException.class);
  }
}