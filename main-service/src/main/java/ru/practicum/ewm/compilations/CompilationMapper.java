package ru.practicum.ewm.compilations;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.EventMapper;
import ru.practicum.ewm.events.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CompilationMapper {
    public static Compilation fromNewCompilationDto(NewCompilationDto newCompilationDto, Set<Event> events) {
        if (newCompilationDto == null) {
            return null;
        }
        Compilation compilation = new Compilation();
        compilation.setEvents(events);
        if (newCompilationDto.getPinned() == null)
            compilation.setPinned(false);
        else
            compilation.setPinned(newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        if (compilation == null) {
            return null;
        }
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());

        compilationDto.setEvents(new EventShortDto[compilation.getEvents().size()]);
        int i = 0;
        for (Event event : compilation.getEvents()) {
            compilationDto.getEvents()[i++] = EventMapper.toEventShortDto(event);
        }

        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        return compilationDto;
    }

    public static Compilation fromUpdateCompilationDto(UpdateCompilationRequest updateRequest,
                                                       Compilation compilation, Set<Event> events) {
        if (updateRequest == null) {
            return null;
        }
        if (events != null)
            compilation.setEvents(events);
        if (updateRequest.getPinned() != null)
            compilation.setPinned(updateRequest.getPinned());
        if (updateRequest.getTitle() != null)
            compilation.setTitle(updateRequest.getTitle());
        return compilation;
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation compilation : compilations) {
            compilationDtoList.add(toCompilationDto(compilation));
        }
        return compilationDtoList;
    }
}
