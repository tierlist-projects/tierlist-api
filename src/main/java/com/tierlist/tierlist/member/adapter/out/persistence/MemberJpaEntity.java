package com.tierlist.tierlist.member.adapter.out.persistence;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class MemberJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, updatable = false, nullable = false)
  private String email;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column(name = "password", nullable = false)
  private String encodedPassword;


  public static MemberJpaEntity from(Member member) {
    Password password = member.getPassword();

    return MemberJpaEntity.builder()
        .nickname(member.getNickname())
        .encodedPassword(password.getEncodedPassword())
        .email(member.getEmail())
        .build();
  }

  public Member toMember() {
    return Member.builder()
        .nickname(this.getNickname())
        .password(Password.fromEncodedPassword(this.getEncodedPassword()))
        .email(this.getEmail())
        .build();
  }
}
