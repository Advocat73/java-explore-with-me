package ru.practicum.ewm.stats.endpointRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EndpointRequestController.class)
public class EndpointRequestControllerTest {
    @MockBean
    private EndpointRequestService endpointRequestService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    private final EasyRandom generator = new EasyRandom();

    @Test
    void addNewRequest() throws Exception {
        EndpointHit endpointHit = createEndpointHit();
        endpointHit.setId(1L);
        when(endpointRequestService.addNewEndpointRequest(endpointHit)).thenReturn(endpointHit);
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString((endpointHit)))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1L), Long.class))
                .andExpect((jsonPath("$.app", is(endpointHit.getApp()))))
                .andExpect(jsonPath("$.ip", is(endpointHit.getIp())))
                .andExpect(jsonPath("$.uri", is(endpointHit.getUri())))
                .andExpect(jsonPath("timestamp", is(endpointHit.getTimestamp())));
    }

    private EndpointHit createEndpointHit() {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp("ewm-main-service");
        Boolean b = generator.nextObject(Boolean.class);
        Integer n = generator.nextInt(5);
        String uri = (b) ? "/events" : "/events/" + n;
        endpointHit.setUri(uri);
        endpointHit.setIp("121.0.0.1");
        endpointHit.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointHit;
    }
}
