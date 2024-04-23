package com.tierlist.tierlist.image.application.domain.service;

import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import com.tierlist.tierlist.image.application.domain.model.ImageFile;
import com.tierlist.tierlist.image.application.port.in.ImageUploadUseCase;
import com.tierlist.tierlist.image.application.port.out.ImageUploader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageUploadService implements ImageUploadUseCase {

  private final ImageUploader imageUploader;

  @Override
  public ImagesResponse upload(List<MultipartFile> images) {
    List<ImageFile> imageFiles = images.stream().map(ImageFile::new).toList();
    List<String> imageNames = imageUploader.uploadImages(imageFiles);
    return new ImagesResponse(imageNames);
  }
}
