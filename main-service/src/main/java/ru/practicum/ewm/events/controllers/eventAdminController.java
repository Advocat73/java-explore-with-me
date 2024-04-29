package ru.practicum.ewm.events.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.EventService;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.events.dto.EventFullDto;
import ru.practicum.ewm.events.dto.UpdateEventAdminRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/events")
public class eventAdminController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    ResponseEntity<EventFullDto> updateEventByAdmin(@RequestBody @Valid UpdateEventAdminRequest updateEvent,
                                                    @PathVariable int eventId) {
        log.info("EVENT_ADMIN_КОНТРОЛЛЕР: PATCH-запрос по эндпоинту admin/events/{}", eventId);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.updateEventByAdmin(updateEvent, eventId));
    }

    @GetMapping
    ResponseEntity<List<EventFullDto>> findEventsByParam(@RequestParam(required = false) int[] users,
                                                         @RequestParam(required = false) State[] states,
                                                         @RequestParam(required = false) int[] categories,
                                                         @RequestParam(required = false) String rangeStart,
                                                         @RequestParam(required = false) String rangeEnd,
                                                         @RequestParam(required = false, defaultValue = "0") Integer from,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("EVENT_ADMIN_КОНТРОЛЛЕР: GET-запрос по эндпоинту admin/events?users={}, states={}, categories={}, " +
                        "rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        List<EventFullDto> lst = eventService.findEventsByParamByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(lst);
    }
}

