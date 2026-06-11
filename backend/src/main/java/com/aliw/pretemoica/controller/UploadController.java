package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.service.ImageStorageService;
import com.aliw.pretemoica.service.ImageStorageService.StoredImage;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

  private final ImageStorageService imageStorageService;

  public UploadController(ImageStorageService imageStorageService) {
    this.imageStorageService = imageStorageService;
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> upload(
      @RequestParam("images") MultipartFile file, @RequestParam("folderName") String folderName)
      throws IOException {

    StoredImage stored = imageStorageService.store(file, folderName);

    // 🔍 Log temporaire
    System.out.println("=== UPLOAD DEBUG ===");
    System.out.println("Filename : " + stored.getFilename());
    System.out.println("URL      : " + stored.getUrl());
    System.out.println("====================");

    return ResponseEntity.ok(
        Map.of(
            "url", stored.getUrl(),
            "filename", stored.getFilename()));
  }
}
