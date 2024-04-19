package ru.practicum.ewm.stats.endpointRequest;

import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestOutDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointRequestMapper {
    public static EndpointRequest fromEndpointRequestInDto(EndpointRequestInDto endpointRequestInDto) {
        if (endpointRequestInDto == null)
            return null;
        EndpointRequest endpointRequest = new EndpointRequest();
        endpointRequest.setAppName(endpointRequestInDto.getApp());
        endpointRequest.setUri(endpointRequestInDto.getUri());
        endpointRequest.setIp(endpointRequestInDto.getIp());
        endpointRequest.setCreatedDate(LocalDateTime.parse(endpointRequestInDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointRequest;
    }

    public static EndpointRequestInDto toEndpointRequestInDto(EndpointRequest endpointRequest) {
        if (endpointRequest == null)
            return null;
        EndpointRequestInDto endpointRequestInDto = new EndpointRequestInDto();
        endpointRequestInDto.setId(endpointRequest.getId());
        endpointRequestInDto.setApp(endpointRequest.getAppName());
        endpointRequestInDto.setUri(endpointRequest.getUri());
        endpointRequestInDto.setIp(endpointRequest.getIp());
        endpointRequestInDto.setTimestamp(endpointRequest.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointRequestInDto;
    }

    public static EndpointRequestOutDto toEndpointRequestOutDto(EndpointRequestShort endpointRequestShort) {
        if (endpointRequestShort == null)
            return null;
        EndpointRequestOutDto endpointRequestOutDto = new EndpointRequestOutDto();
        endpointRequestOutDto.setApp(endpointRequestShort.getAppName());
        endpointRequestOutDto.setUri(endpointRequestShort.getUri());
        endpointRequestOutDto.setHits((int) (long) (endpointRequestShort.getHits()));
        return endpointRequestOutDto;
    }
}

