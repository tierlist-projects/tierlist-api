package com.tierlist.tierlist.item.application.port.in.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ItemResponse {

  private Long id;
  private String name;

}
