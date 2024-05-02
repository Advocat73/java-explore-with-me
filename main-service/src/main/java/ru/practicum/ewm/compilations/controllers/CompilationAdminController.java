package ru.practicum.ewm.compilations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilations.CompilationService;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> addNewCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("COMPILATION_ADMIN_КОНТРОЛЛЕР: POST-запрос по эндпоинту /admin/compilations");
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.addNewCompilation(newCompilationDto));
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@RequestBody @Valid UpdateCompilationRequest updateRequest,
                                                            @PathVariable int compId) {
        log.info("COMPILATION_ADMIN_КОНТРОЛЛЕР: PATCH-запрос по эндпоинту /admin/compilations/{}", compId);
        CompilationDto compilationDto = compilationService.updateCompilation(updateRequest, compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.updateCompilation(updateRequest, compId));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> removeCompilation(@PathVariable int compId) {
        log.info("COMPILATION_ADMIN_КОНТРОЛЛЕР: DELETE-запрос по эндпоинту /admin/compilations/{}", compId);
        compilationService.deleteCompilation(compId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
