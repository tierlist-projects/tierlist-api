package com.tierlist.tierlist.member.application.port.in.service;

import com.tierlist.tierlist.member.application.domain.model.command.MemberSignupCommand;

public interface MemberSignupUseCase {

  Long signup(MemberSignupCommand command);


}
