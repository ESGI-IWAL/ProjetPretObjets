package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.dto.ObjectInfoDisponibilityDto;
import com.aliw.pretemoica.dto.SearchLendingWithIdsObjectsDto;
import com.aliw.pretemoica.mapper.LendingMapper;
import com.aliw.pretemoica.service.LendingService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lendings")
public class LendingController {

  private final LendingService lendingService;

  public LendingController(LendingService lendingService) {
    this.lendingService = lendingService;
  }

  @GetMapping
  public List<LendingDto> getAllLendings() {
    return LendingMapper.toDtoList(lendingService.getAll());
  }

  @PostMapping("/search")
  public List<LendingDto> searchLendings(
      @RequestBody(required = false) LendingSearchDto searchDto) {
    return LendingMapper.toDtoList(lendingService.search(searchDto));
  }

  @GetMapping("/{id}")
  public LendingDto getLendingById(@PathVariable Long id) {
    return LendingMapper.toDto(lendingService.getById(id));
  }

  @PostMapping
  public LendingDto createLending(@RequestBody LendingDto lendingDto) {
    return LendingMapper.toDto(lendingService.create(LendingMapper.toEntity(lendingDto)));
  }

  @DeleteMapping("/{id}")
  public void deleteLending(@PathVariable Long id) {
    lendingService.delete(id);
  }

  @PostMapping("/objects/disponibility")
  public List<ObjectInfoDisponibilityDto> searchObjectsDisponibility(
      @RequestBody SearchLendingWithIdsObjectsDto searchDto) {
    return lendingService.searchObjectsDisponibility(searchDto);
  }
}
