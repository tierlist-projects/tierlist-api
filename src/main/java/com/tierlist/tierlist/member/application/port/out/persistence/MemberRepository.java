package com.tierlist.tierlist.member.application.port.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.Member;
import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  Optional<Member> findByEmail(String email);
}
