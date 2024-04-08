package com.tierlist.tierlist.member.application.domain.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SendEmailVerificationCommand {

  String email;

}
