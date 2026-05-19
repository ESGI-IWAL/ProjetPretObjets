package com.aliw.pretemoica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.mapper.LendingMapper;
import com.aliw.pretemoica.service.LendingService;

@RestController
@RequestMapping("/lending")
public class LendingController {

  private final LendingService lendingervice;

  public LendingController(LendingService lendingervice) {
    this.lendingervice = lendingervice;
  }

  @GetMapping
  public List<LendingDto> getAlllending() {
    return LendingMapper.toDtoList(lendingervice.getAll());
  }

  @GetMapping("/{id}")
  public LendingDto getLendingById(@PathVariable Long id) {
    return LendingMapper.toDto(lendingervice.getById(id));
  }

  @PostMapping
  public LendingDto createLending(@RequestBody LendingDto lendingDto) {
    return LendingMapper.toDto(lendingervice.create(LendingMapper.toEntity(lendingDto)));
  }

  @DeleteMapping("/{id}")
  public void deleteLending(@PathVariable Long id) {
    lendingervice.delete(id);
  }
}
