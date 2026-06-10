package com.aliw.pretemoica.service;

import com.aliw.pretemoica.config.UploadProperties;
import java.io.IOException;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService {

  private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

  private final UploadProperties properties;

  public ImageStorageService(UploadProperties properties) {
    this.properties = properties;
  }

  public String store(MultipartFile file, String folderName) throws IOException {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Le fichier est vide");
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("Type de fichier non autorise");
    }

    String safeFolderName = sanitizeFolderName(folderName);
    if (safeFolderName.isBlank()) {
      throw new IllegalArgumentException("Nom de dossier invalide");
    }

    String extension =
        switch (contentType) {
          case "image/jpeg" -> ".jpg";
          case "image/png" -> ".png";
          case "image/webp" -> ".webp";
          default -> throw new IllegalArgumentException("Extension non supportee");
        };

    Path root = Paths.get(properties.getDir()).toAbsolutePath().normalize();
    Path targetDir = root.resolve(safeFolderName).normalize();

    if (!targetDir.startsWith(root)) {
      throw new IllegalArgumentException("Chemin invalide");
    }

    Files.createDirectories(targetDir);

    String fileName = UUID.randomUUID() + extension;
    Path targetFile = targetDir.resolve(fileName).normalize();
    file.transferTo(targetFile.toFile());

    return properties.getPublicPath() + "/" + safeFolderName + "/" + fileName;
  }

  private String sanitizeFolderName(String input) {
    if (input == null) return "";
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    return normalized.toLowerCase().replaceAll("[^a-z0-9-_ ]", "").trim().replaceAll("\\s+", "-");
  }
}
