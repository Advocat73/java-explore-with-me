package ru.practicum.ewm.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.users.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("USER_КОНТРОЛЛЕР: POST-запрос по эндпоинту /admin/users");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUser(userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(required = false) int[] ids,
                                                   @RequestParam(required = false, defaultValue = "0") int from,
                                                   @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("USER_КОНТРОЛЛЕР: GET-запрос по эндпоинту /admin/users?ids={}, from={}, size={}", ids, from, size);
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUsers(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        log.info("USER_КОНТРОЛЛЕР: DELETE-запрос по эндпоинту /admin/users/{}", userId);
        userService.removeUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

