package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.LendingMapper;
import com.aliw.pretemoica.service.LendingService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lendings")
public class LendingController {

  private final LendingService lendingService;

  public LendingController(LendingService lendingService) {
    this.lendingService = lendingService;
  }

  @GetMapping
  public ResponseEntity<List<LendingDto>> getAllLendings() {
    return ResponseEntity.ok(LendingMapper.toDtoList(lendingService.getAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LendingDto> getLendingById(@PathVariable Long id) {
    return ResponseEntity.ok(LendingMapper.toDto(lendingService.getById(id)));
  }

  @PostMapping
  public ResponseEntity<Long> createLending(@RequestBody CreateLendingDto lendingDto) {
    try {
      LendingDto created = LendingMapper.toDto(lendingService.create(lendingDto));
      return ResponseEntity.status(HttpStatus.CREATED).body(created.getId());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteLending(@PathVariable Long id) {
    lendingService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
