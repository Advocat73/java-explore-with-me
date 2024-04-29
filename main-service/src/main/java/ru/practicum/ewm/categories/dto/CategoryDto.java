package ru.practicum.ewm.categories.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.validation.NotWithSpace;

@Data
public class CategoryDto {
    int id;
    @NotWithSpace(message = "Поле: name. Ошибка: Строка не должна быть пустой и не должна содержать пробелы")
    @Length(min = 1, max = 50, message = "Поле: name. Ошибка: длина не соответствует установленной")
    String name;
}
