package ru.practicum.ewm.events;

import ru.practicum.ewm.events.dto.*;

import java.util.List;

public interface EventService {
    EventFullDto addNewEvent(NewEventDto newEventDto, int userId);

    EventFullDto updateEventByAdmin(UpdateEventAdminRequest updateEvent, int eventId);

    EventFullDto updateEventByInitiator(UpdateEventUserRequest updateEvent, int initiatorId, int eventId);

    List<EventFullDto> findEventsByParamByAdmin(int[] users, State[] states, int[] categories, String rangeStart,
                                                String rangeEnd, int from, int size);

    List<EventFullDto> findInitiatorEvents(int initiatorId, int from, int size);

    EventFullDto findEvent(int requesterId, int eventId);

    List<EventShortDto> findEventsByPublicRequest(String text, int[] categories, Boolean paid, String rangeStart,
                                                  String rangeEnd, Boolean onlyAvailable, String sort, int from, int size, String ipAddress);

    EventFullDto findEventByPublicRequest(int eventId, String ipAddress);


}
