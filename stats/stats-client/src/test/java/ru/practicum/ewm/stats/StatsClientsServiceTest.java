package ru.practicum.ewm.stats;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Data
public class StatsClientsServiceTest {
    private final StatsClientService statsClientService;

    @Test
    void addNewRequest() {
        EndpointHit endpointHit = createEndpointHit();
        EndpointHit endpointHitSaved = statsClientService.addNewRequest(endpointHit).getBody();
        assert endpointHitSaved != null;
        assertEquals(endpointHitSaved.getUri(), endpointHit.getUri());
    }

    @Test
    void getViewStats() {
        EndpointHit endpointHit = createEndpointHit();
        String uri = endpointHit.getUri();
        statsClientService.addNewRequest(endpointHit).getBody();
        ViewStats[] viewStats = statsClientService.getViewStats(
                LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                new String[]{uri}, true).getBody();
        assert viewStats != null;
        assertEquals(viewStats[0].getHits(), 1);
    }

    private EndpointHit createEndpointHit() {
        Random random = new Random();
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp("ewm-main-service");
        boolean b = random.nextBoolean();
        int n = random.nextInt(5);
        String uri = (b) ? "/events" : "/events/" + n;
        endpointHit.setUri(uri);
        endpointHit.setIp("121.0.0.1");
        endpointHit.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointHit;
    }
}
