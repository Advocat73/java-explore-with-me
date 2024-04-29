package ru.practicum.ewm.requests;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.EventRepository;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.requests.dto.ParticipationRequestDto;
import ru.practicum.ewm.users.User;
import ru.practicum.ewm.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserRequestServiceImpl implements UserRequestService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserRequestRepository userRequestRepository;

    @Override
    public ParticipationRequestDto addNewUserRequest(int requesterId, int eventId) {
        log.info("REQUEST_СЕРВИС: Отправлен запрос на добавление нового запроса от пользователя c Id {} на добавлению к событию c Id: {}", requesterId, eventId);
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с ID: " + requesterId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с ID: " + eventId));

        if (requesterId == event.getInitiator().getId())
            throw new ConflictException("Инициатор события не может подать заявку на участие в нем");
        if (!event.getState().equals(State.PUBLISHED))
            throw new ConflictException("Событие не опубликовано: нельзя подать заявку");
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit())
            throw new ConflictException("Событие заполнено: мест нет");
        Status status = Status.PENDING;
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            status = Status.CONFIRMED;
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        UserRequest userRequest = new UserRequest(0, LocalDateTime.now(), requester, event, status);
        log.info("REQUEST_СЕРВИС: userRequest создан");
        return UserRequesterMapper.toParticipationRequestDto(userRequestRepository.save(userRequest));
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatusByInitiator(EventRequestStatusUpdateRequest updateRequest, int requesterId, int eventId) {
        log.info("REQUEST_СЕРВИС: Отправлен запрос на изменение статуса запроса на присоединение к событию c Id: {}", eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с ID: " + eventId));
        if (updateRequest.getStatus().equals(UpdateStatus.CONFIRMED) &&
                event.getParticipantLimit() != 0 &&
                event.getConfirmedRequests() == event.getParticipantLimit())
            throw new ConflictException("Событие заполнено: мест нет");
        List<UserRequest> requestList = userRequestRepository.findAllById(List.of(updateRequest.getRequestIds()));
        EventRequestStatusUpdateResult requestResult = new EventRequestStatusUpdateResult();
        for (UserRequest request : requestList) {
            if (request.getStatus().equals(Status.CONFIRMED)) {
                if (updateRequest.getStatus().equals(UpdateStatus.CONFIRMED))
                    requestResult.getConfirmedRequests().add(UserRequesterMapper.toParticipationRequestDto(request));
                else
                    throw new ConflictException("Нельзя отказать, если ранее заявка была подтверждена");
            } else if (request.getStatus().equals(Status.PENDING)) {
                if (updateRequest.getStatus().equals(UpdateStatus.CONFIRMED)) {
                    request.setStatus(Status.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    requestResult.getConfirmedRequests().add(UserRequesterMapper.toParticipationRequestDto(request));
                } else {
                    request.setStatus(Status.REJECTED);
                    requestResult.getRejectedRequests().add(UserRequesterMapper.toParticipationRequestDto(request));
                }
            } else if (request.getStatus().equals(Status.REJECTED))
                requestResult.getRejectedRequests().add(UserRequesterMapper.toParticipationRequestDto(request));
        }
        userRequestRepository.saveAll(requestList);
        eventRepository.save(event);
        return requestResult;
    }

    @Override
    public List<ParticipationRequestDto> findRequestsByUser(int requesterId) {
        log.info("REQUEST_СЕРВИС: Отправлен запрос на поиск запроса на присоединение к событию от пользователя Id: {}", requesterId);
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с ID: " + requesterId));
        return UserRequesterMapper.toParticipationRequestDtoList(userRequestRepository.findAllByRequesterId(requesterId));
    }

    @Override
    public List<ParticipationRequestDto> findEventRequestsByInitiator(int eventId, int initiatorId) {
        log.info("REQUEST_СЕРВИС: Отправлен запрос на поиск запросов на участие в событии  с Id: {} от пользователя с Id: {}", eventId, initiatorId);
        List<UserRequest> requests = userRequestRepository.findAllByEventIdAndInitiatorId(eventId, initiatorId);
        return UserRequesterMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto cancelRequestByRequester(int requestId, int userId) {
        log.info("REQUEST_СЕРВИС: Отправлен запрос на отмену запроса на участие в событии. Запрос с Id: {} от пользователя с Id: {}", requestId, userId);
        UserRequest userRequest = userRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Нет запроса с ID: " + requestId));
        userRequest.setStatus(Status.CANCELED);
        return UserRequesterMapper.toParticipationRequestDto(userRequestRepository.save(userRequest));
    }
}
