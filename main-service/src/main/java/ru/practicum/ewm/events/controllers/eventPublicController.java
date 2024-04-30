package ru.practicum.ewm.events.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.EventService;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/events")
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    ResponseEntity<List<EventShortDto>> findEventsByParamPublic(@RequestParam(required = false) String text,
                                                                @RequestParam(required = false) int[] categories,
                                                                @RequestParam(required = false) Boolean paid,
                                                                @RequestParam(required = false) String rangeStart,
                                                                @RequestParam(required = false) String rangeEnd,
                                                                @RequestParam(required = false) Boolean onlyAvailable,
                                                                @RequestParam(required = false) String sort,
                                                                @RequestParam(defaultValue = "0") int from,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                HttpServletRequest request) {
        log.info("EVENT_PUBLIC_CONTROLLER: GET-запрос по эндпоинту /events?text={}, categories={}, paid={}, " +
                        "rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}", text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        List<EventShortDto> lst = eventService.findEventsByPublicRequest(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
        return ResponseEntity.status(HttpStatus.OK).body(lst);
    }

    @GetMapping("/{eventId}")
    ResponseEntity<EventFullDto> findEventPublic(@PathVariable int eventId, HttpServletRequest request) {
        log.info("EVENT_PUBLIC_CONTROLLER: GET-запрос по эндпоинту /events/{}", eventId);

        EventFullDto eventDto = eventService.findEventByPublicRequest(eventId, request);
        return ResponseEntity.status(HttpStatus.OK).body(eventDto);
    }
}

