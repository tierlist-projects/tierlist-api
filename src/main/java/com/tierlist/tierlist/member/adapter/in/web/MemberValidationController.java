package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.application.domain.validation.Nickname;
import com.tierlist.tierlist.member.application.port.in.service.MemberValidationUseCase;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberValidationController {

  private final MemberValidationUseCase memberValidationUseCase;

  @GetMapping("/email/unique")
  public ResponseEntity<Void> validateEmailDuplication(@RequestParam @Email String email) {
    memberValidationUseCase.validateEmailDuplication(email);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/nickname/unique")
  public ResponseEntity<Void> validateNicknameDuplication(
      @RequestParam @Nickname String nickname) {
    memberValidationUseCase.validateNicknameDuplication(nickname);
    return ResponseEntity.ok().build();
  }


}
