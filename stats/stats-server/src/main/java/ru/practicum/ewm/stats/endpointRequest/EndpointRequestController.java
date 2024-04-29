package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestOutDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping
@Validated
public class EndpointRequestController {
    private final EndpointRequestService endpointRequestService;

    @PostMapping("/hit")
    public EndpointRequestInDto addNewRequest(@RequestBody EndpointRequestInDto endpointRequestInDto) {
        log.info("ENDPOINT_REQUEST_SERVER_КОНТРОЛЛЕР: POST-запрос по эндпоинту /hit");
        return endpointRequestService.addNewEndpointRequest(endpointRequestInDto);
    }

    @GetMapping("/stats")
    public EndpointRequestOutDto[] getEndpointRequestList(@NotNull(message = "Не указано время начала поиска")
                                                          @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                          @RequestParam LocalDateTime start,
                                                          @NotNull(message = "Не указано время конца поиска")
                                                          @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                          @RequestParam LocalDateTime end,
                                                          @RequestParam(required = false) String[] uris,
                                                          @RequestParam(defaultValue = "false", required = false) Boolean unique) {
        log.info("ENDPOINT_REQUEST_SERVER_КОНТРОЛЛЕР: GET-запрос по эндпоинту /stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        return endpointRequestService.findEndpointRequestList(start, end, uris, unique);
    }
}

