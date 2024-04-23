package com.tierlist.tierlist.image.adapter.out.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.tierlist.tierlist.image.application.domain.model.ImageFile;
import com.tierlist.tierlist.image.application.exception.ImageUploadException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class S3ImageUploaderTest {

  @Mock
  private S3Client s3Client;

  private S3ImageUploader s3ImageUploader;

  @BeforeEach
  void init() {
    s3ImageUploader = new S3ImageUploader(s3Client);
  }

  @Test
  void 이미지를_업로드할_수_있다() {

    // given
    List<ImageFile> imageFiles = List.of(
        new ImageFile(new MockMultipartFile("image-name", new byte[]{})),
        new ImageFile(new MockMultipartFile("image-name", new byte[]{}))
    );

    // when
    List<String> fileNames = s3ImageUploader.uploadImages(imageFiles);

    // then
    assertThat(fileNames).hasSize(2);
    verify(s3Client, times(2))
        .putObject((PutObjectRequest) any(), (RequestBody) any());
  }

  @Test
  void 이미지_서버에_문제가_있을_시_예외를_반환한다() {

    // given
    List<ImageFile> imageFiles = List.of(
        new ImageFile(new MockMultipartFile("image-name", new byte[]{})),
        new ImageFile(new MockMultipartFile("image-name", new byte[]{}))
    );

    given(s3Client.putObject((PutObjectRequest) any(), (RequestBody) any()))
        .willThrow(S3Exception.builder().build());

    // when
    // then
    assertThatThrownBy(() -> s3ImageUploader.uploadImages(imageFiles)).isInstanceOf(
        ImageUploadException.class);
  }

}