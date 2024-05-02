package ru.practicum.ewm.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.events.Location;
import ru.practicum.ewm.validation.MinAvailableDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @Length(min = 20, max = 2000, message = "Поле: annotation. Ошибка: длина не соответствует установленной")
    private String annotation;
    @NotNull
    private Integer category;
    @NotBlank
    @Length(min = 20, max = 7000, message = "Поле: description. Ошибка: длина не соответствует установленной")
    private String description;
    @NotBlank
    @MinAvailableDate
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    @Length(min = 3, max = 120, message = "Поле: title. Ошибка: длина не соответствует установленной")
    private String title;
}
