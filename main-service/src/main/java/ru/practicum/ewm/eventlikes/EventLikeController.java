package ru.practicum.ewm.eventlikes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.EventService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/eventlikes")
public class EventLikeController {
    private final EventLikeService eventLikeService;

    @PostMapping("/event/{eventId}/user/{userId}")
    public ResponseEntity<EventLike> addNewEventLike(@PathVariable int eventId,
                                                     @PathVariable int userId) {
        log.info("EVENT_LIKE_CONTROLLER: POST-запрос по эндпоинту eventlikes/event/{}/user/{}", eventId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventLikeService.addNewEventLike(eventId, userId));
    }

    @DeleteMapping("//{eventLikeId}/user/{userId}")
    ResponseEntity<Void> updateEventByInitiator(@PathVariable int eventLikeId,
                                                @PathVariable int userId) {
        log.info("EVENT_LIKE_CONTROLLER: DELETE-запрос по эндпоинту {}/user/{}", eventLikeId, userId);
        eventLikeService.deleteEventLike(eventLikeId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
