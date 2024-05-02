package ru.practicum.ewm.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/requests")
public class UserRequestController {
    private final UserRequestService userRequestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addNewParticipationRequest(
            @PathVariable int userId,
            @RequestParam Integer eventId) {
        log.info("REQUEST_КОНТРОЛЛЕР: POST-запрос по эндпоинту /users/{}/requests?eventId={}", userId, eventId);
        ParticipationRequestDto participationRequestDto = userRequestService.addNewUserRequest(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(participationRequestDto);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequestByRequester(@PathVariable int userId, @PathVariable int requestId) {
        log.info("REQUEST_КОНТРОЛЛЕР: PATCH-запрос по эндпоинту /users/{}/requests/{}/cancel", requestId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(userRequestService.cancelRequestByRequester(requestId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> findRequestsByUser(@PathVariable int userId) {
        log.info("REQUEST_КОНТРОЛЛЕР: GET-запрос по эндпоинту /users/{}/requests", userId);
        return ResponseEntity.status(HttpStatus.OK).body(userRequestService.findRequestsByUser(userId));
    }
}
