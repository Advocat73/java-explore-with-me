package ru.practicum.ewm.compilations;

import ru.practicum.ewm.compilations.dto.CompilationDto;
import ru.practicum.ewm.compilations.dto.NewCompilationDto;
import ru.practicum.ewm.compilations.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilation(UpdateCompilationRequest updateRequest, int compId);

    List<CompilationDto> findAllCompilations(Boolean pinned, int from, int size);

    CompilationDto findCompilation(int compId);

    void deleteCompilation(int compId);
}
