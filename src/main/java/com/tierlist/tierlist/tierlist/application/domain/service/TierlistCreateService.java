package com.tierlist.tierlist.tierlist.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.tierlist.application.domain.model.Tierlist;
import com.tierlist.tierlist.tierlist.application.domain.model.command.TierlistCreateCommand;
import com.tierlist.tierlist.tierlist.application.port.in.service.TierlistCreateUseCase;
import com.tierlist.tierlist.tierlist.application.port.out.persistence.TierlistRepository;
import com.tierlist.tierlist.topic.application.domain.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierlistCreateService implements TierlistCreateUseCase {

  private final TierlistRepository tierlistRepository;

  private final TopicRepository topicRepository;
  private final MemberRepository memberRepository;

  @Transactional
  @Override
  public Long create(String email, TierlistCreateCommand command) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    if (!topicRepository.existsById(command.getTopicId())) {
      throw new TopicNotFoundException();
    }

    Tierlist tierlist = tierlistRepository.save(Tierlist.builder()
        .title(command.getTitle())
        .topicId(command.getTopicId())
        .commentCount(0)
        .memberId(member.getId())
        .build());

    return tierlist.getId();
  }
}
