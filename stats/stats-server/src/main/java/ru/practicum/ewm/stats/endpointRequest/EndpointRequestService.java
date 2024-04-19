package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestOutDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class EndpointRequestService {
    private final EndpointRequestRepository repository;

    EndpointRequestInDto addNewEndpointRequest(EndpointRequestInDto endpointRequestInDto) {
        EndpointRequest endpointRequest = EndpointRequestMapper.fromEndpointRequestInDto(endpointRequestInDto);
        return EndpointRequestMapper.toEndpointRequestInDto(repository.save(endpointRequest));
    }

    EndpointRequestOutDto[] findEndpointRequestList(String start, String end, String[] uris, Boolean unique) {
        List<EndpointRequestShort> endpointRequestShortList;
        if (uris == null)
            endpointRequestShortList = repository.findAllEventsWithoutUris(
                    LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else {
            if (!unique)
                endpointRequestShortList = repository.findEventsByUris(
                        List.of(uris),
                        LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            else
                endpointRequestShortList = repository.findEventsByUrisDistinct(
                        List.of(uris),
                        LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return endpointRequestShortList
                .stream()
                .map(EndpointRequestMapper::toEndpointRequestOutDto)
                .toArray(EndpointRequestOutDto[]::new);
    }
}
