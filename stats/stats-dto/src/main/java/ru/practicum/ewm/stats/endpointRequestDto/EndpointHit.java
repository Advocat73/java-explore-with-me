package ru.practicum.ewm.stats.endpointRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}

