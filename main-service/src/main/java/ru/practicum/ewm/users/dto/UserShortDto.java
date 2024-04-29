package ru.practicum.ewm.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    private int id;
    @NotBlank
    @Length(min = 2, max = 250, message = "Поле: name. Ошибка: длина не соответствует установленной")
    String name;
}
