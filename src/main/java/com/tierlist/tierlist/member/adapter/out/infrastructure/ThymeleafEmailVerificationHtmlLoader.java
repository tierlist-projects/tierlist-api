package com.tierlist.tierlist.member.adapter.out.infrastructure;

import com.tierlist.tierlist.member.application.domain.model.EmailVerificationCode;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Component
public class ThymeleafEmailVerificationHtmlLoader implements EmailVerificationHtmlLoader {

  private static final String TEMPLATE = "email-verification-template";
  private final SpringTemplateEngine templateEngine;

  @Override
  public String loadWith(EmailVerificationCode code) {
    HashMap<String, String> emailValues = new HashMap<>();
    emailValues.put("code", code.getCode());

    Context context = new Context();
    emailValues.forEach(context::setVariable);

    return templateEngine.process(TEMPLATE, context);
  }
}
