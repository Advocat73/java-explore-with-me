package ru.practicum.ewm.events.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.users.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SearchEventFilter {
    private User initiator;
    private List<User> initiators;
    private String text;
    private List<Category> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private State state;
    private List<State> states;
}
