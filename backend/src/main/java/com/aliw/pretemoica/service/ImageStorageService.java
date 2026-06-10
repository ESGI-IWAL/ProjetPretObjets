package com.aliw.pretemoica.service;

import com.aliw.pretemoica.config.UploadProperties;
import java.io.IOException;
import java.nio.file.*;
import java.text.Normalizer;
import java.time.Instant;
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

  /**
   * Stocke le fichier dans <uploads dir> et renvoie l'URL publique et le nom de fichier généré. Le
   * nom généré : {slug(folderName)}__{timestamp}_{uuid}{ext}
   */
  public StoredImage store(MultipartFile file, String folderName) throws IOException {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Le fichier est vide");
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
      throw new IllegalArgumentException("Type de fichier non autorisé : " + contentType);
    }

    String extension = extensionFromContentType(contentType);

    // slug basé sur le folderName (peut être vide)
    String slug = sanitize(folderName);
    if (slug.isBlank()) {
      slug = "img";
    }

    String timestamp = String.valueOf(Instant.now().toEpochMilli());
    String uuid = UUID.randomUUID().toString();

    String generatedFileName = String.format("%s__%s_%s%s", slug, timestamp, uuid, extension);

    Path root = Paths.get(properties.getDir()).toAbsolutePath().normalize();
    Files.createDirectories(root); // crée uploads/ si besoin

    Path targetFile = root.resolve(generatedFileName).normalize();

    // Sécurité supplémentaire : éviter l'écriture hors du dossier uploads
    if (!targetFile.startsWith(root)) {
      throw new IllegalStateException("Chemin de destination invalide");
    }

    file.transferTo(targetFile.toFile());

    String url = properties.getPublicPath() + "/" + generatedFileName;
    return new StoredImage(url, generatedFileName);
  }

  private String sanitize(String input) {
    if (input == null) return "";
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    String slug =
        normalized
            .toLowerCase()
            .replaceAll("[^a-z0-9-_ ]", "") // autorise lettres/nombres/-,_
            .trim()
            .replaceAll("\\s+", "-");
    return slug;
  }

  private String extensionFromContentType(String contentType) {
    return switch (contentType) {
      case "image/jpeg" -> ".jpg";
      case "image/png" -> ".png";
      case "image/webp" -> ".webp";
      default -> throw new IllegalArgumentException(
          "Extension inconnue pour le content-type: " + contentType);
    };
  }

  // DTO interne simple
  public static class StoredImage {
    private final String url;
    private final String filename;

    public StoredImage(String url, String filename) {
      this.url = url;
      this.filename = filename;
    }

    public String getUrl() {
      return url;
    }

    public String getFilename() {
      return filename;
    }
  }
}
