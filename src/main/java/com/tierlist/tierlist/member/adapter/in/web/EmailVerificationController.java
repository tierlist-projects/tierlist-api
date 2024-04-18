package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.adapter.in.web.dto.EmailVerificationConfirmRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.SendEmailVerificationRequest;
import com.tierlist.tierlist.member.application.domain.model.command.EmailVerificationConfirmCommand;
import com.tierlist.tierlist.member.application.domain.model.command.SendEmailVerificationCommand;
import com.tierlist.tierlist.member.application.port.in.service.EmailVerificationUseCase;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class EmailVerificationController {

  private final EmailVerificationUseCase emailVerificationUseCase;

  @PostMapping("/email/verification/request")
  public ResponseEntity<Void> sendVerificationMail(
      @RequestBody @Valid SendEmailVerificationRequest request) {
    SendEmailVerificationCommand command = request.toCommand();
    emailVerificationUseCase.sendVerificationEmail(command);
    return ResponseEntity.status(HttpServletResponse.SC_CREATED).build();
  }

  @PostMapping("/email/verification/confirm")
  public ResponseEntity<Void> confirmEmailVerification(
      @RequestBody @Valid EmailVerificationConfirmRequest request) {

    EmailVerificationConfirmCommand command = request.toCommand();

    if (emailVerificationUseCase.verifyEmail(command)) {
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).build();
  }

}
