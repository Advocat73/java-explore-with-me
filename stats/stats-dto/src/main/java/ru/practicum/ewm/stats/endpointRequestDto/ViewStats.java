package ru.practicum.ewm.stats.endpointRequestDto;

import lombok.Data;

@Data
public class ViewStats {
    private String app;
    private String uri;
    private Integer hits;
}
