package com.tierlist.tierlist.member.application.port.in.service;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;

public interface MemberInformationUseCase {

  MemberResponse getMemberInformation(String email);

}
