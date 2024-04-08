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
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService implements MemberSignupUseCase {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public Long signup(MemberSignupCommand command) {

    validateEmailDuplication(command.getEmail());
    validateNicknameDuplication(command.getNickname());

    Member member = memberRepository.save(command.toMember(passwordEncoder));

    return member.getId();
  }

  @Override
  public void validateEmailDuplication(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new EmailDuplicationException();
    }
  }

  @Override
  public void validateNicknameDuplication(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new NicknameDuplicationException();
    }
  }
}
