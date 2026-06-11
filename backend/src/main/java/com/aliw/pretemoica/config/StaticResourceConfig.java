package com.aliw.pretemoica.config;

import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

  private final UploadProperties properties;

  public StaticResourceConfig(UploadProperties properties) {
    this.properties = properties;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String absolutePath = Paths.get(properties.getDir()).toAbsolutePath().normalize().toString();

    registry
        .addResourceHandler(properties.getPublicPath() + "/**")
        .addResourceLocations("file:" + absolutePath + "/");
  }
}
