package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.CreateObjectDto;
import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.ObjectMapper;
import com.aliw.pretemoica.service.ObjectService;
import com.aliw.pretemoica.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objects")
public class ObjectController {

  private final ObjectService objectService;
  private final UserService userService;

  public ObjectController(ObjectService objectService, UserService userService) {
    this.objectService = objectService;
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<ObjectDto>> getAllObjects() {
    return ResponseEntity.ok(ObjectMapper.toDtoList(objectService.getAll()));
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
  public ResponseEntity<Long> createObject(
      @Valid @RequestBody CreateObjectDto createObjectDto, @RequestParam Long ownedById) {
    try {
      ObjectDto createdObject = objectService.create(createObjectDto, ownedById);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdObject.getId());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObjectDto> updateObject(
      @PathVariable Long id, @Valid @RequestBody UpdateObjectDto updateObjectDto) {
    try {
      ObjectDto updatedObject = objectService.update(id, updateObjectDto);
      return ResponseEntity.ok(updatedObject);
    } catch (ResourceNotFoundException e) {
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
