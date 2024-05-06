package ru.practicum.ewm.eventlikes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventLikeResponse {
    String eventTitle;
    long likes;
}

