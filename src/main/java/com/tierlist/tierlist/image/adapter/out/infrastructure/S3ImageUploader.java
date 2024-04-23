package com.tierlist.tierlist.image.adapter.out.infrastructure;

import com.tierlist.tierlist.image.application.domain.model.ImageFile;
import com.tierlist.tierlist.image.application.exception.ImageUploadException;
import com.tierlist.tierlist.image.application.port.out.ImageUploader;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
@Component
public class S3ImageUploader implements ImageUploader {

  private static final String CACHE_CONTROL_VALUE = "max-age=3153600";
  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucket;

  @Override
  public List<String> uploadImages(List<ImageFile> imageFiles) {
    return imageFiles.stream().map(this::uploadImage).toList();
  }

  private String uploadImage(ImageFile imageFile) {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucket)
        .key(imageFile.getHashName())
        .contentType(imageFile.getContentType())
        .contentLength(imageFile.getSize())
        .cacheControl(CACHE_CONTROL_VALUE)
        .build();

    try {
      RequestBody requestBody = RequestBody.fromBytes(imageFile.getBytes());
      s3Client.putObject(putObjectRequest, requestBody);
    } catch (IOException e) {
      throw new ImageUploadException();
    }

    return imageFile.getHashName();
  }
}
