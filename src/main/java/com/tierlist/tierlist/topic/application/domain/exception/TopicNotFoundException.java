package com.tierlist.tierlist.topic.application.domain.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.NotFoundException;

public class TopicNotFoundException extends NotFoundException {

  public TopicNotFoundException() {
    super(ErrorCode.TOPIC_NOT_FOUND_ERROR);
  }
}
