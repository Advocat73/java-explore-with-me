package ru.practicum.ewm.eventlikes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.EventMapper;
import ru.practicum.ewm.events.EventRepository;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.users.User;
import ru.practicum.ewm.users.UserRepository;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class EventLikeServiceImpl implements EventLikeService {
    private final EventLikeRepository eventLikeRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public EventLike addNewEventLike(int eventId, int userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с Id: " + eventId));
        if (!event.getState().equals(State.PUBLISHED))
            throw new BadRequestException("Чтобы поставить лайк статус события с Id: " + eventId + " должен быть PUBLISHED");
        if (event.getInitiator().getId() == userId)
            throw new ConflictException("Инициатор события с Id: " + eventId + " не может ставить лайк");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с Id: " + userId));
        try {
            return eventLikeRepository.save(new EventLike(event, user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Пользователь с Id: " + userId + " уже поставил лайк событию с Id: " + eventId);
        }
    }

    @Override
    public void deleteEventLike(int eventLikeId, int userId) {
        EventLike eventLike = eventLikeRepository.findById(eventLikeId)
                .orElseThrow(() -> new NotFoundException("Не найдено лайка с Id: " + eventLikeId));
        if (eventLike.getUser().getId() != userId)
            throw new ConflictException("Удалить лайк может только тот пользователь, который его поставил");
        eventLikeRepository.delete(eventLike);
    }

    @Override
    public List<EventLikeResponse> findPopularEventsByLikes() {
        return eventLikeRepository.findPopularEventsByLikes();
    }

    @Override
    public List<EventFullDto> findEventsByUserLikes(int userId) {
        List<Event> events = eventLikeRepository.findEventsByUserLikes(userId);
        return EventMapper.toEventFullDtoList(events);
    }
}
