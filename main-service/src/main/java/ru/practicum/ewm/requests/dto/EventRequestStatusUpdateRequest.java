package ru.practicum.ewm.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.requests.UpdateStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotNull
    @NotEmpty
    Integer[] requestIds;
    @NotNull
    UpdateStatus status;
}
