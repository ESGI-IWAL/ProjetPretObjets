package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.CreateObjectDto;
import com.aliw.pretemoica.dto.ObjectDto;
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
  public List<ObjectDto> getAllObjects() {
    return ObjectMapper.toDtoList(objectService.getAll());
  }

  @GetMapping("/{id}")
  public ObjectDto getObjectById(@PathVariable Long id) {
    return ObjectMapper.toDto(objectService.getById(id));
  }

  @PostMapping
  public ResponseEntity<ObjectDto> createObject(
      @Valid @RequestBody CreateObjectDto createObjectDto, @RequestParam Long ownedById) {
    try {
      ObjectDto createdObject = objectService.create(createObjectDto, ownedById);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public void deleteObject(@PathVariable Long id) {
    objectService.delete(id);
  }
}
