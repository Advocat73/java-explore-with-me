package ru.practicum.ewm.compilations;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewm.events.Event;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer>, JpaSpecificationExecutor<Event> {
    List<Compilation> findAllByPinnedFalse(PageRequest pageRequest);

    List<Compilation> findAllByPinnedTrue(PageRequest pageRequest);
}
