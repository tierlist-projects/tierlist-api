package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.adapter.in.web.dto.MemberSignupRequest;
import com.tierlist.tierlist.member.application.port.in.MemberSignupUseCase;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

  private final MemberSignupUseCase memberSignupUseCase;

  @PostMapping
  public ResponseEntity<Void> signup(@RequestBody MemberSignupRequest request) {
    Long memberId = memberSignupUseCase.signup(request.toCommand());
    return ResponseEntity.created(URI.create("/member/" + memberId)).build();
  }

  @GetMapping("/email/unique")
  public ResponseEntity<Void> validateEmailDuplication(@RequestParam String email) {
    memberSignupUseCase.validateEmailDuplication(email);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/nickname/unique")
  public ResponseEntity<Void> validateNicknameDuplication(@RequestParam String nickname) {
    memberSignupUseCase.validateNicknameDuplication(nickname);
    return ResponseEntity.ok().build();
  }

}
