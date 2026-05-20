package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.mapper.ObjectMapper;
import com.aliw.pretemoica.service.ObjectService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objects")
public class ObjectController {

  private final ObjectService objectService;

  public ObjectController(ObjectService objectService) {
    this.objectService = objectService;
  }

  @GetMapping
  public List<ObjectDto> getAllObjects() {
    return ObjectMapper.toDtoList(objectService.getAll());
  }

  @GetMapping("/{id}")
  public ObjectDto getObjectById(@PathVariable Long id) {
    return ObjectMapper.toDto(objectService.getById(id));
  }

  @PostMapping
  public ObjectDto createObject(@RequestBody ObjectDto objectDto) {
    return ObjectMapper.toDto(objectService.create(ObjectMapper.toEntity(objectDto)));
  }

  @DeleteMapping("/{id}")
  public void deleteObject(@PathVariable Long id) {
    objectService.delete(id);
  }
}
