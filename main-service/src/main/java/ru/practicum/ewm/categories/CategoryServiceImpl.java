package ru.practicum.ewm.categories;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto addNewCategory(CategoryDto categoryDto) {
        log.info("CATEGORY_СЕРВИС: отправлен запрос на добавление категории {}", categoryDto.getName());
        Category category = categoryRepository.save(CategoryMapper.fromCategoryDto(categoryDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(int catId, String name) {
        log.info("CATEGORY_СЕРВИС: отправлен запрос на изменение категории с Id{}", catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Нет категории с Id: " + catId));
        category.setName(name);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto findCategory(int catId) {
        log.info("CATEGORY_СЕРВИС: отправлен запрос на получение категории событий");
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Нет категории с Id: " + catId));
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> findCategories(int from, int size) {
        log.info("CATEGORY_СЕРВИС: отправлен запрос на получение категорий событий");
        return CategoryMapper.toCategoryDtoList(categoryRepository.findAll(createPageRequest(from, size)).getContent());
    }

    @Override
    public void removeCategory(int catId) {
        log.info("CATEGORY_СЕРВИС: отправлен запрос на удаление категории событий");
        categoryRepository.deleteById(catId);
    }

    private PageRequest createPageRequest(int from, int size) {
        Sort sortBy = Sort.by("Id").ascending();
        int page = (from < size) ? 0 : from / size;
        return PageRequest.of(page, size, sortBy);
    }
}
