package com.tierlist.tierlist.topic.application.exception;

import com.tierlist.tierlist.global.error.ErrorCode;
import com.tierlist.tierlist.global.error.exception.DuplicationException;

public class TopicDuplicationException extends DuplicationException {

  public TopicDuplicationException() {
    super(ErrorCode.TOPIC_NAME_DUPLICATION_ERROR);
  }
}
