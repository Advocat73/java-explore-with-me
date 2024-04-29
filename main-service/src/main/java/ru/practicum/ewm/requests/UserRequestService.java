package ru.practicum.ewm.requests;

import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface UserRequestService {
    ParticipationRequestDto addNewUserRequest(int requesterId, int eventId);

    EventRequestStatusUpdateResult updateRequestStatusByInitiator(EventRequestStatusUpdateRequest updateRequest, int requesterId, int eventId);

    List<ParticipationRequestDto> findRequestsByUser(int requesterId);

    List<ParticipationRequestDto> findEventRequestsByInitiator(int eventId, int initiatorId);

    ParticipationRequestDto cancelRequestByRequester(int requestId, int userId);
}