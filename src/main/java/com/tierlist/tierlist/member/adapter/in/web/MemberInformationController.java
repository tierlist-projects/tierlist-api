package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.port.in.service.MemberInformationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberInformationController {

  private final MemberInformationUseCase memberInformationUseCase;

  @GetMapping("/me")
  public ResponseEntity<MemberResponse> getOwnInformation(@AuthenticationPrincipal String email) {
    return ResponseEntity.ok(memberInformationUseCase.getMemberInformation(email));
  }
}
