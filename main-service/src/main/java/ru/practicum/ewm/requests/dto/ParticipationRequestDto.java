package ru.practicum.ewm.requests.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    String created;
    int event;
    int id;
    int requester;
    String status;
}
