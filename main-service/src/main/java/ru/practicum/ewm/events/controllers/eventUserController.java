package ru.practicum.ewm.events.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.EventService;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.NewEventDto;
import ru.practicum.ewm.events.dto.UpdateEventUserRequest;
import ru.practicum.ewm.requests.UserRequestService;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/events")
public class eventUserController {
    private final EventService eventService;
    private final UserRequestService userRequestService;

    @PostMapping
    public ResponseEntity<EventFullDto> addNewEvent(@RequestBody @Valid NewEventDto newEventDto,
                                                    @PathVariable int userId) {
        log.info("EVENT_USER_CONTROLLER: POST-запрос по эндпоинту /users/{}/events", userId);
        EventFullDto eventDto = eventService.addNewEvent(newEventDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventDto);
    }

    @PatchMapping("/{eventId}")
    ResponseEntity<EventFullDto> updateEventByInitiator(@RequestBody @Valid UpdateEventUserRequest updateEvent,
                                                        @PathVariable int userId,
                                                        @PathVariable int eventId) {
        log.info("EVENT_USER_CONTROLLER: PATCH-запрос по эндпоинту /users/{}/events/{}", userId, eventId);
        EventFullDto eventDto = eventService.updateEventByInitiator(updateEvent, userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventDto);
    }

    @PatchMapping("/{eventId}/requests")
    ResponseEntity<EventRequestStatusUpdateResult> updateRequestStatusByInitiator(@RequestBody @Valid EventRequestStatusUpdateRequest updateRequest,
                                                                                  @PathVariable int userId,
                                                                                  @PathVariable int eventId) {
        log.info("EVENT_USER_CONTROLLER: PATCH-запрос по эндпоинту /users/{}/events/{}/requests", userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(
                userRequestService.updateRequestStatusByInitiator(updateRequest, userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    ResponseEntity<List<ParticipationRequestDto>> findEventRequestsByUser(@PathVariable int userId, @PathVariable int eventId) {
        log.info("EVENT_USER_CONTROLLER: GET-запрос по эндпоинту /users/{}/events/{}/requests", userId, eventId);
        List<ParticipationRequestDto> requestDtoList = userRequestService.findEventRequestsByInitiator(eventId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(requestDtoList);
    }

    @GetMapping
    ResponseEntity<List<EventFullDto>> findInitiatorEvents(@PathVariable int userId,
                                                           @RequestParam(defaultValue = "0") int from,
                                                           @RequestParam(defaultValue = "10") int size) {
        log.info("EVENT_USER_CONTROLLER: GET-запрос по эндпоинту user/{}/events?from={}, size={}", userId, from, size);
        List<EventFullDto> eventDtoList = eventService.findInitiatorEvents(userId, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(eventDtoList);
    }

    @GetMapping("/{eventId}")
    ResponseEntity<EventFullDto> findEvent(@PathVariable int userId, @PathVariable int eventId) {
        log.info("EVENT_USER_CONTROLLER: GET-запрос по эндпоинту /users/{}/events/{}", userId, eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findEvent(userId, eventId));
    }
}

