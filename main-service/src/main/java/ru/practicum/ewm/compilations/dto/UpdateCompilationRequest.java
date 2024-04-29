package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    private Integer[] events;
    private Boolean pinned;
    @Length(min = 1, max = 50, message = "Поле: title. Ошибка: длина не соответствует установленной")
    private String title;
}
