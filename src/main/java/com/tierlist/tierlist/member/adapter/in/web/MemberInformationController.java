package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.adapter.in.web.dto.request.ChangeMemberNicknameRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.request.ChangeMemberPasswordRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.request.ChangeMemberProfileImageRequest;
import com.tierlist.tierlist.member.adapter.in.web.dto.response.MemberResponse;
import com.tierlist.tierlist.member.application.port.in.service.MemberInformationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PatchMapping("/me/nickname")
  public ResponseEntity<Void> updateNickname(@AuthenticationPrincipal String email,
      @RequestBody @Valid ChangeMemberNicknameRequest request) {
    memberInformationUseCase.changeMemberNickname(email, request.toCommand());
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/me/profile-image")
  public ResponseEntity<Void> updateProfileImage(@AuthenticationPrincipal String email,
      @RequestBody ChangeMemberProfileImageRequest request) {
    memberInformationUseCase.changeMemberProfileImage(email, request.toCommand());
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/me/password")
  public ResponseEntity<Void> updateProfileImage(@AuthenticationPrincipal String email,
      @RequestBody @Valid ChangeMemberPasswordRequest request) {
    memberInformationUseCase.changeMemberPassword(email, request.toCommand());
    return ResponseEntity.ok().build();
  }

}
