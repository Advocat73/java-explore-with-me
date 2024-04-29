package ru.practicum.ewm.events;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.compilations.Compilation;
import ru.practicum.ewm.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank
    @Length(min = 20, max = 2000, message = "Поле: annotation. Ошибка: длина не соответствует установленной")
    private String annotation;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @PositiveOrZero
    int confirmedRequests = 0;
    private LocalDateTime createdOn = LocalDateTime.now();
    @NotBlank
    @Length(min = 20, max = 7000, message = "Поле: description. Ошибка: длина не соответствует установленной")
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private float lat;
    private float lon;
    private Boolean paid;
    @PositiveOrZero
    private int participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    @NotBlank
    @Length(min = 3, max = 120, message = "Поле: title. Ошибка: длина не соответствует установленной")
    private String title;
    private int views = 0;
    @ElementCollection
    @CollectionTable(name = "ip_addresses", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "ip_address")
    private Set<String> ipAddresses = new HashSet<>();
    @ManyToMany(mappedBy = "events")
    Set<Compilation> compilations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
