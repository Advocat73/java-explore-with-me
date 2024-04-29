package ru.practicum.ewm.stats.endpointRequestDto;

import lombok.Data;

@Data
public class EndpointHit {
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}

