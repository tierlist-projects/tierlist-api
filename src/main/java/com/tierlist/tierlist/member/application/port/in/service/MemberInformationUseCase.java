package com.tierlist.tierlist.member.application.port.in.service;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberNicknameCommand;
import com.tierlist.tierlist.member.application.domain.model.command.ChangeMemberProfileImageCommand;

public interface MemberInformationUseCase {

  MemberResponse getMemberInformation(String email);

  void changeMemberNickname(String email, ChangeMemberNicknameCommand command);

  void changeMemberProfileImage(String email, ChangeMemberProfileImageCommand command);
}
