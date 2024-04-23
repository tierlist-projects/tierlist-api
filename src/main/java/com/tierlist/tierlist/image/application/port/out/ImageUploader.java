package com.tierlist.tierlist.image.application.port.out;

import com.tierlist.tierlist.image.application.domain.model.ImageFile;
import java.util.List;

public interface ImageUploader {

  List<String> uploadImages(final List<ImageFile> multipartFile);
}
