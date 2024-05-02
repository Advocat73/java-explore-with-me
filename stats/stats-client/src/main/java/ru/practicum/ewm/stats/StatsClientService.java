package ru.practicum.ewm.stats;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor

public class StatsClientService {
    private final RestTemplate rest;
    private final String resourceUrl;

    @Autowired
    public StatsClientService(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .build();
        resourceUrl = serverUrl;
    }

    ResponseEntity<EndpointHit> addNewRequest(EndpointHit endpointHit) {
        log.info("STATS_CLIENT_КЛИЕНТ: POST-запрос по эндпоинту /hit");
        ResponseEntity.BodyBuilder responseBuilder;
        ResponseEntity<EndpointHit> statsServiceResponse = rest.postForEntity(resourceUrl + "/hit", endpointHit, EndpointHit.class);

        responseBuilder = (statsServiceResponse.getStatusCode().is2xxSuccessful()) ?
                ResponseEntity.status(HttpStatus.CREATED) :
                ResponseEntity.status(statsServiceResponse.getStatusCode());
        return (statsServiceResponse.hasBody()) ?
                responseBuilder.body(statsServiceResponse.getBody()) :
                responseBuilder.build();
    }

    public ResponseEntity<ViewStats[]> getViewStats(String start, String end, String[] uris, Boolean unique) {
        log.info("STATS_CLIENT_КЛИЕНТ: GET-запрос по эндпоинту /stats");
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        String url = resourceUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
        return rest.getForEntity(url, ViewStats[].class, parameters);
    }
}
