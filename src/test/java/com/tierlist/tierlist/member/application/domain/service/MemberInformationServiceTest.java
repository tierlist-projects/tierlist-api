package com.tierlist.tierlist.member.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
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
    memberRepository = new FakeMemberRepository();
    memberInformationService = new MemberInformationService(memberRepository);
    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
}