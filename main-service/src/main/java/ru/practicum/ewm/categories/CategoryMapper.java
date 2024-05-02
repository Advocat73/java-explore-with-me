package ru.practicum.ewm.categories;

import ru.practicum.ewm.categories.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public static Category fromCategoryDto(CategoryDto categoryDto) {
        if (categoryDto == null)
            return null;
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public static CategoryDto toCategoryDto(Category category) {
        if (category == null)
            return null;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categories) {
            categoryDtoList.add(toCategoryDto(category));
        }
        return categoryDtoList;
    }
}
