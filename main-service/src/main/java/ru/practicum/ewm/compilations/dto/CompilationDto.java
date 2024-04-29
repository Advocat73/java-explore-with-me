package ru.practicum.ewm.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.events.dto.EventShortDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private EventShortDto[] events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
