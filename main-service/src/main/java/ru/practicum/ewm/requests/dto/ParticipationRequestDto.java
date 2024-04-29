package ru.practicum.ewm.requests.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ParticipationRequestDto {
    String created;
    int event;
    int id;
    int requester;
    String status;
}
