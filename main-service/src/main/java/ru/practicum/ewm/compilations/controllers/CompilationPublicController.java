package ru.practicum.ewm.compilations.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.CompilationService;
import ru.practicum.ewm.compilations.dto.CompilationDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/compilations")
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> findAllCompilations(@RequestParam(required = false) Boolean pinned,
                                                                    @RequestParam(defaultValue = "0") int from,
                                                                    @RequestParam(defaultValue = "10") int size) {
        log.info("COMPILATION_PUBLIC_КОНТРОЛЛЕР: GET-запрос по эндпоинту /compilations?sort={}, from={}, size={}", pinned, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.findAllCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> findCompilation(@PathVariable int compId) {
        log.info("COMPILATION_PUBLIC_КОНТРОЛЛЕР: GET-запрос по эндпоинту /compilations/{}", compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.findCompilation(compId));
    }
}
