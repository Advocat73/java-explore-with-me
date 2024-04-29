package ru.practicum.ewm.stats.endpointRequest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;

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

        EndpointRequestInDto endpointRequestInDto = createEndpointRequestInDto();

        when(endpointRequestRepository.save(ArgumentMatchers.any(EndpointRequest.class))).thenAnswer((Answer<EndpointRequest>) invocation -> {
            EndpointRequest en = (EndpointRequest) invocation.getArguments()[0];
            en.setId(1L);
            return en;
        });
        EndpointRequestInDto endpointRequestInDtoReceived = endpointRequestService.addNewEndpointRequest(endpointRequestInDto);
        assertEquals(1L, endpointRequestInDtoReceived.getId());
        assertEquals(endpointRequestInDto.getApp(), endpointRequestInDtoReceived.getApp());
        assertEquals(endpointRequestInDto.getUri(), endpointRequestInDtoReceived.getUri());
        assertEquals(endpointRequestInDto.getIp(), endpointRequestInDtoReceived.getIp());
        assertEquals(endpointRequestInDto.getTimestamp(), endpointRequestInDtoReceived.getTimestamp());
    }

    private EndpointRequestInDto createEndpointRequestInDto() {
        EndpointRequestInDto endpointRequestInDto = new EndpointRequestInDto();
        endpointRequestInDto.setApp("ewm-main-service");
        Boolean b = generator.nextObject(Boolean.class);
        int n = generator.nextInt(5);
        String uri = (b) ? "/events" : "/events/" + n;
        endpointRequestInDto.setUri(uri);
        endpointRequestInDto.setIp("121.0.0.1");
        endpointRequestInDto.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointRequestInDto;
    }
}

