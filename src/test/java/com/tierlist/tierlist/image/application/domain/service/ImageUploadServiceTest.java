package com.tierlist.tierlist.image.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import com.tierlist.tierlist.image.application.port.out.ImageUploader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class ImageUploadServiceTest {

  @Mock
  private ImageUploader imageUploader;

  private ImageUploadService imageUploadService;

  @BeforeEach
  void init() {
    imageUploadService = new ImageUploadService(imageUploader);
  }

  @Test
  void 이미지를_업로드하고_파일_이름을_반환한다() {
    // given
    List<MultipartFile> images = List.of(
        new MockMultipartFile("image-name", new byte[]{}),
        new MockMultipartFile("image-name", new byte[]{})
    );

    given(imageUploader.uploadImages(any())).willReturn(List.of("image-name-1", "image-name-2"));

    // when
    ImagesResponse result = imageUploadService.upload(images);

    // then
    assertThat(result.getImageNames()).hasSize(2);
  }

}