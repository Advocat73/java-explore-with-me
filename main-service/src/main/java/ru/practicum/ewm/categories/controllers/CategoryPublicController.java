package ru.practicum.ewm.categories.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.CategoryService;
import ru.practicum.ewm.categories.dto.CategoryDto;

import java.util.List;

@Validated
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("categories")
public class CategoryPublicController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findCategories(@RequestParam(defaultValue = "0") int from,
                                                            @RequestParam(defaultValue = "10") int size) {
        log.info("CATEGORY_PUBLIC_КОНТРОЛЛЕР: GET-запрос по эндпоинту /categories?from={}, size={}", from, size);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategories(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> findCategory(@PathVariable int catId) {
        log.info("CATEGORY_PUBLIC_КОНТРОЛЛЕР: GET-запрос по эндпоинту /categories/{}", catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findCategory(catId));
    }
}
