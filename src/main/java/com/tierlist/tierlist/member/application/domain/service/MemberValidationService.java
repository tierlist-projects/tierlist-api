package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.EmailDuplicationException;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberValidationService implements MemberValidationUseCase {

  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  @Override
  public void validateEmailDuplication(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new EmailDuplicationException();
    }
  }

  @Transactional(readOnly = true)
  @Override
  public void validateNicknameDuplication(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new NicknameDuplicationException();
    }
  }
}
