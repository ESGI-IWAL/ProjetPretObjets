package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.LendingHistoryDto;
import com.aliw.pretemoica.mapper.LendingHistoryMapper;
import com.aliw.pretemoica.service.LendingHistoryService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lending-history")
public class LendingHistoryController {

  private final LendingHistoryService lendingHistoryService;

  public LendingHistoryController(LendingHistoryService lendingHistoryService) {
    this.lendingHistoryService = lendingHistoryService;
  }

  @GetMapping
  public List<LendingHistoryDto> getAllLendingHistory() {
    return LendingHistoryMapper.toDtoList(lendingHistoryService.getAll());
  }

  @GetMapping("/{id}")
  public LendingHistoryDto getLendingHistoryById(@PathVariable Long id) {
    return LendingHistoryMapper.toDto(lendingHistoryService.getById(id));
  }

  @PostMapping
  public LendingHistoryDto createLendingHistory(@RequestBody LendingHistoryDto lendingHistoryDto) {
    return LendingHistoryMapper.toDto(
        lendingHistoryService.create(LendingHistoryMapper.toEntity(lendingHistoryDto)));
  }

  @DeleteMapping("/{id}")
  public void deleteLendingHistory(@PathVariable Long id) {
    lendingHistoryService.delete(id);
  }
}
