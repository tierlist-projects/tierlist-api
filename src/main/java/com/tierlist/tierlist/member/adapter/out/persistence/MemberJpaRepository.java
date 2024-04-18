package com.tierlist.tierlist.member.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);

  Optional<MemberJpaEntity> findByEmail(String email);
}
