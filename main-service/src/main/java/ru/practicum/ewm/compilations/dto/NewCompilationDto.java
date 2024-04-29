package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private Set<Integer> events = new HashSet<>();
    private Boolean pinned;
    @NotBlank
    @Length(min = 1, max = 50, message = "Поле: title. Ошибка: длина не соответствует установленной")
    private String title;
}
