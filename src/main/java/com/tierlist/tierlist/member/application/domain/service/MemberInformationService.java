package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberNicknameCommand;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberPasswordCommand;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberProfileImageCommand;
import com.tierlist.tierlist.member.application.port.in.service.MemberInformationUseCase;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberInformationService implements MemberInformationUseCase {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public MemberResponse getMemberInformation(String email) {
    return null;
  }

  @Override
  public void changeMemberNickname(String email, ChangeMemberNicknameCommand command) {

  }

  @Transactional
  @Override
  public void changeMemberProfileImage(String email, ChangeMemberProfileImageCommand command) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    member.changeProfileImage(command.getProfileImageName());
    memberRepository.save(member);
  }

  @Override
  public void changeMemberPassword(String email, ChangeMemberPasswordCommand command) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    member.changePassword(command.getPassword(), command.getNewPassword(), passwordEncoder);
  }
}
