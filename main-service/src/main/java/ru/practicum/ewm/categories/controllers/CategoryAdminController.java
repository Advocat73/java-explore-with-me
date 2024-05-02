package ru.practicum.ewm.categories.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.CategoryService;
import ru.practicum.ewm.categories.dto.CategoryDto;

import javax.validation.Valid;

@Validated
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_КОНТРОЛЛЕР: POST-запрос по эндпоинту /admin/categories");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addNewCategory(categoryDto));
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto,
                                                      @PathVariable int catId) {
        log.info("CATEGORY_ADMIN_КОНТРОЛЛЕР: PATCH-запрос по эндпоинту /admin/categories/{}", catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(catId, categoryDto.getName()));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> removeCategory(@PathVariable int catId) {
        log.info("CATEGORY_ADMIN_КОНТРОЛЛЕР: DELETE-запрос по эндпоинту /admin/categories/{}", catId);
        categoryService.removeCategory(catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
