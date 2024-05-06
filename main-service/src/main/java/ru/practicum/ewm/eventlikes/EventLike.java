package ru.practicum.ewm.eventlikes;

import lombok.*;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "event_likes")
public class EventLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private final Event event;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private final User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventLike)) return false;
        return id.equals(((EventLike) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

