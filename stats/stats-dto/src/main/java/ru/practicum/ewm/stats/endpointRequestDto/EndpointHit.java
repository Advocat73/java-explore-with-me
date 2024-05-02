package ru.practicum.ewm.stats.endpointRequestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class EndpointHit {
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}

