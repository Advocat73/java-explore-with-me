package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EndpointRequestShort {
    private String appName;
    private String uri;
    private Long hits;
}

