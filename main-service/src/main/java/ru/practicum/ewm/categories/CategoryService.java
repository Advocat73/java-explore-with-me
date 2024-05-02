package ru.practicum.ewm.categories;

import ru.practicum.ewm.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addNewCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(int catId, String name);

    CategoryDto findCategory(int catId);

    List<CategoryDto> findCategories(int from, int size);

    void removeCategory(int catId);
}
