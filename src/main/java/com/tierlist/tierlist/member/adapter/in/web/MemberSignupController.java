package com.tierlist.tierlist.member.adapter.in.web;

import com.tierlist.tierlist.member.adapter.in.web.dto.MemberSignupRequest;
import com.tierlist.tierlist.member.application.port.in.service.MemberSignupUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberSignupController {

  private final MemberSignupUseCase memberSignupUseCase;

  @PostMapping
  public ResponseEntity<Void> signup(@RequestBody @Valid MemberSignupRequest request) {
    Long memberId = memberSignupUseCase.signup(request.toCommand());
    return ResponseEntity.created(URI.create("/member/" + memberId)).build();
  }

}
