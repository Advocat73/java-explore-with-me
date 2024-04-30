package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<EndpointHit> addNewRequest(@RequestBody EndpointHit endpointHit) {
            //public EndpointHit addNewRequest(@RequestBody EndpointHit endpointHit) {
        log.info("ENDPOINT_REQUEST_SERVER_КОНТРОЛЛЕР: POST-запрос по эндпоинту /hit");
        return ResponseEntity.status(HttpStatus.CREATED).body(endpointRequestService.addNewEndpointRequest(endpointHit));
        //return endpointRequestService.addNewEndpointRequest(endpointHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<ViewStats[]> getEndpointRequestList(@NotNull(message = "Не указано время начала поиска")
                                                          @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                          @RequestParam LocalDateTime start,
                                                          @NotNull(message = "Не указано время конца поиска")
                                                          @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                          @RequestParam LocalDateTime end,
                                                          @RequestParam(required = false) String[] uris,
                                                          @RequestParam(defaultValue = "false", required = false) Boolean unique) {
        log.info("ENDPOINT_REQUEST_SERVER_КОНТРОЛЛЕР: GET-запрос по эндпоинту /stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        if (start.isAfter(end))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        //ViewStats[] viewStats = endpointRequestService.findEndpointRequestList(start, end, uris, unique);
        //ResponseEntity<ViewStats[]> rvs = ResponseEntity.status(HttpStatus.OK).body(viewStats);
        //return rvs;
        return ResponseEntity.status(HttpStatus.OK).body(endpointRequestService.findEndpointRequestList(start, end, uris, unique));
    }

   /* @GetMapping("/stats")
    public ResponseEntity<Integer> getEndpointRequestList() {
        Integer i = 5;
        ResponseEntity<Integer> r = ResponseEntity.status(HttpStatus.OK).body(5);
        return r;
    }*/
}

