package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.EmailDuplicationException;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberValidationService implements MemberValidationUseCase {

  private final MemberRepository memberRepository;

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
