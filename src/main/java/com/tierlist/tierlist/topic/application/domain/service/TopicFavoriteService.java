package com.tierlist.tierlist.topic.application.domain.service;

import com.tierlist.tierlist.member.application.domain.exception.MemberNotFoundException;
import com.tierlist.tierlist.member.application.domain.model.Member;
import com.tierlist.tierlist.member.application.port.out.persistence.MemberRepository;
import com.tierlist.tierlist.topic.application.domain.model.Topic;
import com.tierlist.tierlist.topic.application.domain.model.TopicFavorite;
import com.tierlist.tierlist.topic.application.exception.TopicNotFoundException;
import com.tierlist.tierlist.topic.application.port.in.service.TopicFavoriteUseCase;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicFavoriteRepository;
import com.tierlist.tierlist.topic.application.port.out.persistence.TopicRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TopicFavoriteService implements TopicFavoriteUseCase {

  private final MemberRepository memberRepository;
  private final TopicRepository topicRepository;
  private final TopicFavoriteRepository topicFavoriteRepository;

  @Transactional
  @Override
  public void toggleFavorite(String email, Long topicId) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(MemberNotFoundException::new);

    Topic topic = topicRepository.findById(topicId)
        .orElseThrow(TopicNotFoundException::new);

    Optional<TopicFavorite> topicFavoriteOptional = topicFavoriteRepository.findByMemberIdAndTopicId(
        member.getId(), topic.getId());

    if (topicFavoriteOptional.isEmpty()) {
      topicFavoriteRepository.save(TopicFavorite.builder()
          .memberId(member.getId())
          .topicId(topic.getId())
          .build());
      topic.addFavorite();
      topicRepository.save(topic);
      return;
    }

    topicFavoriteRepository.delete(topicFavoriteOptional.get());
    topic.removeFavorite();
    topicRepository.save(topic);
  }

}