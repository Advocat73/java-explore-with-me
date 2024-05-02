package ru.practicum.ewm.ewm.events;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.categories.CategoryRepository;
import ru.practicum.ewm.events.*;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.stats.StatsClientController;
import ru.practicum.ewm.stats.StatsClientService;
import ru.practicum.ewm.users.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Data
@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {
    EventService eventService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private final StatsClientService statsClientService;

    @BeforeEach
    public void setUp() {
        StatsClientController statsClientController = new StatsClientController(statsClientService);
        eventService = new EventServiceImpl(eventRepository, userRepository, categoryRepository, statsClientController);
    }

    @Test
    public void findEventByPublicRequest() {
        Event event = new Event();
        event.setState(State.PUBLISHED);
        event.setEventDate(LocalDateTime.now().plusYears(3));
        when(eventRepository.findById(ArgumentMatchers.any(Integer.class))).thenReturn(Optional.of(event));
        eventService.findEventByPublicRequest(1, "0:0:0:0:0:0:0:1", "/events/1");
        eventService.findEventByPublicRequest(1, "0:0:0:0:0:0:0:1", "/events/1");
        EventFullDto eventFullDto = eventService.findEventByPublicRequest(1, "0:0:0:0:0:0:0:2", "/events/1");
        assertEquals(eventFullDto.getViews(), 2);
    }
}
