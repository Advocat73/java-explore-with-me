package ru.practicum.ewm.stats.endpointRequest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EndpointRequestServiceTest {
    @Mock
    EndpointRequestRepository endpointRequestRepository;

    private EndpointRequestService endpointRequestService;

    private final EasyRandom generator = new EasyRandom();

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void addNewRequest() {
        endpointRequestService = new EndpointRequestService(endpointRequestRepository);

        EndpointHit endpointHit = createEndpointHit();

        when(endpointRequestRepository.save(ArgumentMatchers.any(EndpointRequest.class))).thenAnswer((Answer<EndpointRequest>) invocation -> {
            EndpointRequest en = (EndpointRequest) invocation.getArguments()[0];
            en.setId(1L);
            return en;
        });
        EndpointHit endpointRequestInDtoReceived = endpointRequestService.addNewEndpointRequest(endpointHit);
        assertEquals(1L, endpointRequestInDtoReceived.getId());
        assertEquals(endpointHit.getApp(), endpointRequestInDtoReceived.getApp());
        assertEquals(endpointHit.getUri(), endpointRequestInDtoReceived.getUri());
        assertEquals(endpointHit.getIp(), endpointRequestInDtoReceived.getIp());
        assertEquals(endpointHit.getTimestamp(), endpointRequestInDtoReceived.getTimestamp());
    }

    private EndpointHit createEndpointHit() {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp("ewm-main-service");
        Boolean b = generator.nextObject(Boolean.class);
        int n = generator.nextInt(5);
        String uri = (b) ? "/events" : "/events/" + n;
        endpointHit.setUri(uri);
        endpointHit.setIp("121.0.0.1");
        endpointHit.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointHit;
    }
}

