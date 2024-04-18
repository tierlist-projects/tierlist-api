package com.tierlist.tierlist.member.application.port.in.service;

public interface MemberValidationUseCase {

  void validateEmailDuplication(String email);

  void validateNicknameDuplication(String nickname);
}
