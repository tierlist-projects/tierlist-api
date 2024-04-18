package com.tierlist.tierlist.member.adapter.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;

public interface EmailVerificationHtmlLoader {

  String loadWith(EmailVerificationCode code);

}
