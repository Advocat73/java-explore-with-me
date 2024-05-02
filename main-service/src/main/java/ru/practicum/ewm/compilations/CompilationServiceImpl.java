package ru.practicum.ewm.compilations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        log.info("COMPILATION_СЕРВИС: Отправлен запрос админа на сохранение новой подборки для событий с ids {}",
                newCompilationDto.getEvents());
        Set<Event> events = new HashSet<>(eventRepository.findAllByIdIn(new ArrayList<>(newCompilationDto.getEvents())));
        return CompilationMapper.toCompilationDto(
                compilationRepository.save(CompilationMapper.fromNewCompilationDto(newCompilationDto, events)));
    }

    @Override
    public CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, int compId) {
        log.info("COMPILATION_СЕРВИС: Отправлен запрос на изменение подборки для событий с id {}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Не найдено подборка событий с Id: " + compId));
        Set<Event> events = (updateRequest.getEvents() == null) ? null :
                new HashSet<>(eventRepository.findAllByIdIn(List.of(updateRequest.getEvents())));
        return CompilationMapper.toCompilationDto(
                compilationRepository.save(CompilationMapper.fromUpdateCompilationDto(updateRequest, compilation, events)));
    }

    @Override
    public List<CompilationDto> findAllCompilations(Boolean pinned, int from, int size) {
        log.info("COMPILATION_СЕРВИС: Отправлен запрос на получение подборок для событий по параметрам");
        if (pinned == null) pinned = false;
        Sort sortBy = Sort.by("Id").descending();
        int page = (from < size) ? 0 : from / size;
        PageRequest pageRequest = PageRequest.of(page, size, sortBy);
        List<Compilation> compilations = (!pinned) ?
                compilationRepository.findAllByPinnedFalse(pageRequest) :
                compilationRepository.findAllByPinnedTrue(pageRequest);
        return CompilationMapper.toCompilationDtoList(compilations);
    }

    @Override
    public CompilationDto findCompilation(int compId) {
        log.info("COMPILATION_СЕРВИС: Отправлен запрос на получение подборки для событий по Id: {}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Не найдено подборка событий с Id: " + compId));
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilation(int compId) {
        log.info("COMPILATION_СЕРВИС: Отправлен запрос на удаление подборки для событий по Id: {}", compId);
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Не найдено подборка событий с Id: " + compId));
        compilationRepository.delete(compilation);
    }
}
