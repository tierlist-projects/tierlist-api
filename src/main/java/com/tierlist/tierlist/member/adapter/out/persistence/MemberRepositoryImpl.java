package com.tierlist.tierlist.member.adapter.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberJpaRepository memberJpaRepository;

  @Override
  public Member save(Member member) {
    return memberJpaRepository.save(MemberJpaEntity.from(member)).toMember();
  }

  @Override
  public boolean existsByEmail(String email) {
    return memberJpaRepository.existsByEmail(email);
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return memberJpaRepository.existsByNickname(nickname);
  }
}
