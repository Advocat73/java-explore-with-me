package ru.practicum.ewm.stats;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@AllArgsConstructor
public class StatsClientController {
    private final StatsClientService statsClientService;

    public void addNewRequest(String app, String uri, String ip, String timestamp) {
        log.info("STATS_CLIENT_КОНТРОЛЛЕР: POST-запрос по эндпоинту /hit");
        EndpointHit endpointHit = new EndpointHit(0L, app, uri, ip, timestamp);
        statsClientService.addNewRequest(endpointHit);
    }

    public ResponseEntity<ViewStats[]> getEndpointRequestList(String start, String end, String[] uris, Boolean unique) {
        log.info("STATS_CLIENT_КОНТРОЛЛЕР: GET-запрос по эндпоинту /stats");
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return statsClientService.getViewStats(start, end, uris, unique);
    }
}
