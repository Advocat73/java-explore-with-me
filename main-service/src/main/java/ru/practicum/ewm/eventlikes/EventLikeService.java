package ru.practicum.ewm.eventlikes;

import ru.practicum.ewm.events.dto.EventFullDto;

import java.util.List;

public interface EventLikeService {
    EventLike addNewEventLike(int eventId, int userId);

    void deleteEventLike(int eventLikeId, int userId);

    List<EventLikeResponse> findPopularEventsByLikes();

    List<EventFullDto> findEventsByUserLikes(int userId);
}

