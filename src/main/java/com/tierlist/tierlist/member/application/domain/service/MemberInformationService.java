package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.exception.NicknameDuplicationException;
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

  @Transactional(readOnly = true)
  @Override
  public MemberResponse getMemberInformation(String email) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    return MemberResponse.fromMember(member);
  }

  @Transactional
  @Override
  public void changeMemberNickname(String email, ChangeMemberNicknameCommand command) {
    if (memberRepository.existsByNickname(command.getNickname())) {
      throw new NicknameDuplicationException();
    }

    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

    member.changeNickname(command.getNickname());
    memberRepository.save(member);

  }

  @Transactional
  @Override
  public void changeMemberProfileImage(String email, ChangeMemberProfileImageCommand command) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    member.changeProfileImage(command.getProfileImageName());
    memberRepository.save(member);
  }

  @Transactional
  @Override
  public void changeMemberPassword(String email, ChangeMemberPasswordCommand command) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    member.changePassword(command.getPassword(), command.getNewPassword(), passwordEncoder);
  }
}
