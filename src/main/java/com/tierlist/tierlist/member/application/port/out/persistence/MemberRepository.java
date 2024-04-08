package com.tierlist.tierlist.member.application.port.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.Member;

public interface MemberRepository {

  Member save(Member member);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);
}
