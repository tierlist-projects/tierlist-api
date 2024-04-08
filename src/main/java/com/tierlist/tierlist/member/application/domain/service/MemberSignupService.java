package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.command.MemberSignupCommand;
import com.tierlist.tierlist.member.application.port.in.service.MemberSignupUseCase;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberSignupService implements MemberSignupUseCase {

  private final MemberValidationUseCase memberValidationUseCase;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public Long signup(MemberSignupCommand command) {

    memberValidationUseCase.validateEmailDuplication(command.getEmail());
    memberValidationUseCase.validateNicknameDuplication(command.getNickname());

    Member member = memberRepository.save(command.toMember(passwordEncoder));

    return member.getId();
  }

}
