package com.tierlist.tierlist.member.application.domain.service;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberNicknameCommand;
import com.tierlist.tierlist.member.application.port.in.service.MemberInformationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberInformationService implements MemberInformationUseCase {

  @Override
  public MemberResponse getMemberInformation(String email) {
    return null;
  }

  @Override
  public void changeMemberNickname(String email, ChangeMemberNicknameCommand command) {

  }
}
