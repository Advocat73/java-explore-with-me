package ru.practicum.ewm.requests.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.requests.UpdateStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {
    @NotNull
    @NotEmpty
    Integer[] requestIds;
    @NotNull
    UpdateStatus status;
}
