package ru.practicum.ewm.stats.endpointRequestDto;

import lombok.Data;

@Data
public class EndpointRequestInDto {
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}

