package com.tierlist.tierlist.support.member;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {

  private List<Member> data = new ArrayList<>();
  private Long autoGeneratedId = 0L;

  @Override
  public Member save(Member member) {
    Long id = member.getId();
    if (id == null || id == 0) {
      Member newMember = Member.builder()
          .id(++autoGeneratedId)
          .email(member.getEmail())
          .nickname(member.getNickname())
          .password(member.getPassword())
          .profileImage(member.getProfileImage())
          .build();
      data.add(newMember);
      return newMember;
    }

    data.removeIf(item -> item.getId().equals(id));
    data.add(member);
    return member;
  }

  @Override
  public boolean existsByEmail(String email) {
    return data.stream().anyMatch(item -> item.getEmail().equals(email));
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return data.stream().anyMatch(item -> item.getNickname().equals(nickname));
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    return data.stream().filter(item -> item.getEmail().equals(email)).findFirst();
  }
}
