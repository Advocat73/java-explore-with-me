package ru.practicum.ewm.requests;

import ru.practicum.ewm.requests.dto.ParticipationRequestDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserRequesterMapper {
    public static ParticipationRequestDto toParticipationRequestDto(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        ParticipationRequestDto participationRequestDto = new ParticipationRequestDto();
        participationRequestDto.setId(userRequest.getId());
        participationRequestDto.setCreated(userRequest.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        participationRequestDto.setRequester(userRequest.getRequester().getId());
        participationRequestDto.setEvent(userRequest.getEvent().getId());
        participationRequestDto.setStatus(userRequest.getStatus().toString());
        return participationRequestDto;
    }


    public static List<ParticipationRequestDto> toParticipationRequestDtoList(List<UserRequest> userRequestList) {
        List<ParticipationRequestDto> participationRequestDtoList = new ArrayList<>();
        for (UserRequest request : userRequestList) {
            participationRequestDtoList.add(toParticipationRequestDto(request));
        }
        return participationRequestDtoList;
    }
}
