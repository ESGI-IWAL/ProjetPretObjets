package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.CreateObjectDto;
import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.ObjectSearchDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.ObjectMapper;
import com.aliw.pretemoica.service.ObjectService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objects")
public class ObjectController {

  private final ObjectService objectService;

  public ObjectController(ObjectService objectService) {
    this.objectService = objectService;
  }

  @GetMapping
  public ResponseEntity<List<ObjectDto>> getAllObjects() {
    return ResponseEntity.ok(ObjectMapper.toDtoList(objectService.getAll()));
  }

  @PostMapping("/search")
  public ResponseEntity<List<ObjectDto>> searchObjects(
      @RequestBody(required = false) ObjectSearchDto searchDto) {
    return ResponseEntity.ok(ObjectMapper.toDtoList(objectService.search(searchDto)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ObjectDto> getObjectById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(ObjectMapper.toDto(objectService.getById(id)));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<Object> createObject(@Valid @RequestBody CreateObjectDto createObjectDto) {
    try {
      ObjectDto createdObject = objectService.create(createObjectDto, createObjectDto.getOwnerId());
      return ResponseEntity.status(HttpStatus.CREATED).body(createdObject.getId());
    } catch (IllegalArgumentException e) {
      // retourne le message de l'exception pour faciliter le debug côté client
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObjectDto> updateObject(
      @PathVariable Long id, @Valid @RequestBody UpdateObjectDto updateObjectDto) {
    try {
      ObjectDto updatedObject = objectService.update(id, updateObjectDto);
      return ResponseEntity.ok(updatedObject);
    } catch (ResourceNotFoundException rnfe) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteObject(@PathVariable Long id) {
    objectService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
