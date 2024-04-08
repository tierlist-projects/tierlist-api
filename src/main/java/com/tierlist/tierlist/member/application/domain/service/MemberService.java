package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.EmailDuplicationException;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.command.MemberSignupCommand;
import com.tierlist.tierlist.member.application.port.in.MemberSignupUseCase;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements MemberSignupUseCase {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Long signup(MemberSignupCommand command) {

    if (memberRepository.existsByNickname(command.getNickname())) {
      throw new NicknameDuplicationException();
    }

    if (memberRepository.existsByEmail(command.getEmail())) {
      throw new EmailDuplicationException();
    }

    Member member = memberRepository.save(command.toMember(passwordEncoder));

    return member.getId();
  }
}
