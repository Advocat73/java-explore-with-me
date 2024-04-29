package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointHit;
import ru.practicum.ewm.stats.endpointRequestDto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EndpointRequestService {
    private final EndpointRequestRepository repository;

    EndpointHit addNewEndpointRequest(EndpointHit endpointHit) {
        EndpointRequest endpointRequest = EndpointRequestMapper.fromEndpointRequestInDto(endpointHit);
        return EndpointRequestMapper.toEndpointRequestInDto(repository.save(endpointRequest));
    }

    ViewStats[] findEndpointRequestList(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<EndpointRequestShort> endpointRequestShortList = (uris == null) ?
                repository.findAllEventsWithoutUris(start, end) :
                (!unique) ?
                        repository.findEventsByUris(List.of(uris), start, end) :
                        repository.findEventsByUrisDistinct(List.of(uris), start, end);

        return endpointRequestShortList
                .stream()
                .map(EndpointRequestMapper::toViewStats)
                .toArray(ViewStats[]::new);
    }
}
