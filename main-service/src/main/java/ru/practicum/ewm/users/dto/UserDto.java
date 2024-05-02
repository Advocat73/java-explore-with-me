package ru.practicum.ewm.users.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserDto {
    private int id;
    @NotBlank
    @Length(min = 2, max = 250, message = "Поле: name. Ошибка: длина не соответствует установленной")
    String name;
    @NotBlank
    @Length(min = 6, max = 254, message = "Поле: email. Ошибка: длина не соответствует установленной.")
    @Email(message = "Передан некорректный e-mail адрес")
    String email;
}

