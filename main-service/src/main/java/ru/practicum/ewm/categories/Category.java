package ru.practicum.ewm.categories;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.validation.NotWithSpace;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotWithSpace(message = "Поле: name. Ошибка: Строка не должна пустой и не должна содержать пробелы")
    @Length(min = 1, max = 50, message = "Поле: name. Ошибка: длина не соответствует установленной")
    @Column(unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        return id != null && id.equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
