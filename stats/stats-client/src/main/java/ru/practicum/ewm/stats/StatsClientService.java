package ru.practicum.ewm.stats;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor

public class StatsClientService {
    private static final String STATS_SERVER_URL = "http://localhost:9090";
    private final RestTemplate rest;
    private final String resourceUrl;

    @Autowired
    public StatsClientService(RestTemplateBuilder builder) {
        rest = new RestTemplate();
        resourceUrl = STATS_SERVER_URL;
    }

    ResponseEntity<EndpointHit> addNewRequest(EndpointHit endpointHit) {
        log.info("STATS_CLIENT_КЛИЕНТ: POST-запрос по эндпоинту /hit");
        ResponseEntity.BodyBuilder responseBuilder;
        ResponseEntity<EndpointHit> statsServiceResponse = rest.postForEntity(resourceUrl + "/hit", endpointHit, EndpointHit.class);

        if (statsServiceResponse.getStatusCode().is2xxSuccessful())
            responseBuilder = ResponseEntity.status(HttpStatus.CREATED);
        else {
            responseBuilder = ResponseEntity.status(statsServiceResponse.getStatusCode());
        }
        if (statsServiceResponse.hasBody()) {
            return responseBuilder.body(statsServiceResponse.getBody());
        }
        return responseBuilder.build();
    }

    public ViewStats[] getViewStats(String start, String end, String[] uris, Boolean unique) {
        log.info("STATS_CLIENT_КЛИЕНТ: GET-запрос по эндпоинту /stats");
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        String url = resourceUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
        ResponseEntity<ViewStats[]> viewStats = rest.getForEntity(resourceUrl, ViewStats[].class, parameters);
        //ResponseEntity<Integer> r = rest.getForEntity(resourceUrl, Integer.class, parameters);
        //ResponseEntity<Integer> r = rest.getForEntity(resourceUrl, Integer.class);
        //return  null;
        return viewStats.getBody();
    }
}
