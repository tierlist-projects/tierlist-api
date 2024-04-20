package com.tierlist.tierlist.image.application.domain.service;

import com.tierlist.tierlist.image.adapter.in.web.dto.response.ImagesResponse;
import com.tierlist.tierlist.image.application.port.out.ImageUploadUseCase;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService implements ImageUploadUseCase {

  @Override
  public ImagesResponse upload(List<MultipartFile> images) {
    return null;
  }
}
