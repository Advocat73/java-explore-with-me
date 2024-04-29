package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestOutDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EndpointRequestService {
    private final EndpointRequestRepository repository;

    EndpointRequestInDto addNewEndpointRequest(EndpointRequestInDto endpointRequestInDto) {
        EndpointRequest endpointRequest = EndpointRequestMapper.fromEndpointRequestInDto(endpointRequestInDto);
        return EndpointRequestMapper.toEndpointRequestInDto(repository.save(endpointRequest));
    }

    EndpointRequestOutDto[] findEndpointRequestList(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<EndpointRequestShort> endpointRequestShortList = (uris == null) ?
                repository.findAllEventsWithoutUris(start, end) :
                (!unique) ?
                        repository.findEventsByUris(List.of(uris), start, end) :
                        repository.findEventsByUrisDistinct(List.of(uris), start, end);

        return endpointRequestShortList
                .stream()
                .map(EndpointRequestMapper::toEndpointRequestOutDto)
                .toArray(EndpointRequestOutDto[]::new);
    }
}
