package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.CreateObjectDto;
import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.ObjectSearchDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.ObjectMapper;
import com.aliw.pretemoica.security.SecurityUtils;
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

  /** GET /objects/me - retourne les objets appartenant à l'utilisateur connecté */
  @GetMapping("/me")
  public ResponseEntity<List<ObjectDto>> getMyObjects() {
    try {
      Long currentUserId = SecurityUtils.getCurrentUserId();
      return ResponseEntity.ok(ObjectMapper.toDtoList(objectService.getAllByOwner(currentUserId)));
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/search")
  public ResponseEntity<List<ObjectDto>> searchObjects(
      @RequestBody(required = false) ObjectSearchDto searchDto) {

    if (searchDto == null
        || (searchDto.getName() == null
            && searchDto.getStateOfWear() == null
            && searchDto.getCategory() == null
            && searchDto.getMaterial() == null)) {
      return ResponseEntity.ok(ObjectMapper.toDtoList(objectService.getAll()));
    }

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
  public ResponseEntity<?> createObject(@Valid @RequestBody CreateObjectDto createObjectDto) {
    try {
      Long currentUserId = SecurityUtils.getCurrentUserId();
      ObjectDto createdObject = objectService.create(createObjectDto, currentUserId);
      // Pratique standard : renvoyer l'ID ou le DTO complet
      return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateObject(
      @PathVariable Long id, @Valid @RequestBody UpdateObjectDto updateObjectDto) {
    try {
      // Hypothèse : Ton service a peut-être besoin de l'ID utilisateur pour valider la modification
      // ?
      // Long currentUserId = SecurityUtils.getCurrentUserId();

      ObjectDto updatedObject = objectService.update(id, updateObjectDto);
      return ResponseEntity.ok(updatedObject);
    } catch (ResourceNotFoundException rnfe) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      // Permet de voir l'erreur réelle dans tes logs de console
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteObject(@PathVariable Long id) {
    objectService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
