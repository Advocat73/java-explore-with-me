package ru.practicum.ewm.stats.endpointRequest;

import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointRequestMapper {
    public static EndpointRequest fromEndpointRequestInDto(EndpointHit endpointRequestInDto) {
        if (endpointRequestInDto == null)
            return null;
        EndpointRequest endpointRequest = new EndpointRequest();
        endpointRequest.setAppName(endpointRequestInDto.getApp());
        endpointRequest.setUri(endpointRequestInDto.getUri());
        endpointRequest.setIp(endpointRequestInDto.getIp());
        endpointRequest.setCreatedDate(LocalDateTime.parse(endpointRequestInDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointRequest;
    }

    public static EndpointHit toEndpointRequestInDto(EndpointRequest endpointRequest) {
        if (endpointRequest == null)
            return null;
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setId(endpointRequest.getId());
        endpointHit.setApp(endpointRequest.getAppName());
        endpointHit.setUri(endpointRequest.getUri());
        endpointHit.setIp(endpointRequest.getIp());
        endpointHit.setTimestamp(endpointRequest.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointHit;
    }

    public static ViewStats toViewStats(EndpointRequestShort endpointRequestShort) {
        if (endpointRequestShort == null)
            return null;
        ViewStats viewStats = new ViewStats();
        viewStats.setApp(endpointRequestShort.getAppName());
        viewStats.setUri(endpointRequestShort.getUri());
        viewStats.setHits((int) (long) (endpointRequestShort.getHits()));
        return viewStats;
    }
}

