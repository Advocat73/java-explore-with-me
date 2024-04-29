package ru.practicum.ewm.events;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.categories.CategoryMapper;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.users.User;
import ru.practicum.ewm.users.dto.UserShortDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {
    public static Event fromNewEventDto(NewEventDto newEventDto, Category category, User initiator) {
        if (newEventDto == null) {
            return null;
        }
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setInitiator(initiator);
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        event.setPaid(newEventDto.getPaid() != null && newEventDto.getPaid());
        event.setParticipantLimit((newEventDto.getParticipantLimit() == null) ? 0 : newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.getRequestModeration() == null || newEventDto.getRequestModeration());
        event.setState(State.PENDING);
        event.setTitle(newEventDto.getTitle());
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        if (event == null) {
            return null;
        }
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setCreatedOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setId(event.getId());
        eventFullDto.setInitiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()));
        eventFullDto.setLocation(new Location(event.getLat(), event.getLon()));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        if (event.getPublishedOn() != null)
            eventFullDto.setPublishedOn(event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState().toString());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setViews(event.getViews());
        return eventFullDto;
    }

    public static List<EventFullDto> toEventFullDtoList(List<Event> events) {
        List<EventFullDto> eventFullDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFullDtoList.add(toEventFullDto(event));
        }
        return eventFullDtoList;
    }

    public static EventShortDto toEventShortDto(Event event) {
        if (event == null) {
            return null;
        }
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setViews(event.getViews());
        return eventShortDto;
    }

    public static List<EventShortDto> toEventShortDtoList(List<Event> events) {
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event event : events) {
            eventShortDtoList.add(toEventShortDto(event));
        }
        return eventShortDtoList;
    }

    public static Event fromUpdateEventAdminRequest(UpdateEventAdminRequest updateEvent, Event event, Category category) {
        if (updateEvent.getAnnotation() != null)
            event.setAnnotation(updateEvent.getAnnotation());
        if (category != null)
            event.setCategory(category);
        if (updateEvent.getDescription() != null)
            event.setDescription(updateEvent.getDescription());
        if (updateEvent.getEventDate() != null)
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (updateEvent.getLocation() != null) {
            event.setLat(updateEvent.getLocation().getLat());
            event.setLon(updateEvent.getLocation().getLon());
        }
        if (updateEvent.getPaid() != null)
            event.setPaid(updateEvent.getPaid());
        if (updateEvent.getParticipantLimit() != null)
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        if (updateEvent.getRequestModeration() != null)
            event.setRequestModeration(updateEvent.getRequestModeration());
        if (updateEvent.getStateAction() != null)
            if ((updateEvent.getStateAction() == StateActionForAdminRequest.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                event.setState(State.REJECTED);
            }
        if (updateEvent.getTitle() != null)
            event.setTitle(updateEvent.getTitle());
        return event;
    }

    public static Event fromUpdateEventInitiatorRequest(UpdateEventUserRequest updateEvent, Event event, Category category) {
        if (updateEvent.getAnnotation() != null)
            event.setAnnotation(updateEvent.getAnnotation());
        if (category != null)
            event.setCategory(category);
        if (updateEvent.getDescription() != null)
            event.setDescription(updateEvent.getDescription());
        if (updateEvent.getEventDate() != null)
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (updateEvent.getLocation() != null) {
            event.setLat(updateEvent.getLocation().getLat());
            event.setLon(updateEvent.getLocation().getLon());
        }
        if (updateEvent.getPaid() != null)
            event.setPaid(updateEvent.getPaid());
        if (updateEvent.getParticipantLimit() != null)
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        if (updateEvent.getRequestModeration() != null)
            event.setRequestModeration(updateEvent.getRequestModeration());
        if ((updateEvent.getStateAction() == StateActionForUserRequest.CANCEL_REVIEW)) {
            event.setState(State.CANCELED);
        } else {
            event.setState(State.PENDING);
            event.setPublishedOn(LocalDateTime.now());
        }
        event.setPublishedOn(LocalDateTime.now());
        if (updateEvent.getTitle() != null)
            event.setTitle(updateEvent.getTitle());
        return event;
    }
}
