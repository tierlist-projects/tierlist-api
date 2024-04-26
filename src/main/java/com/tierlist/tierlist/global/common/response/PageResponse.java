package com.tierlist.tierlist.global.common.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {

  private int totalPages;
  private long totalElements;
  private int size;
  private List<T> content;

  public static <T> PageResponse<T> fromPage(Page<T> page) {
    return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), page.getSize(),
        page.getContent());
  }

}
