package ru.practicum.ewm.requests;

import lombok.*;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_requests")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequest)) return false;
        return id != null && id.equals(((UserRequest) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
