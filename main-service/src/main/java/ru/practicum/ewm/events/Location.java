package ru.practicum.ewm.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private float lat;
    private float lon;
}
