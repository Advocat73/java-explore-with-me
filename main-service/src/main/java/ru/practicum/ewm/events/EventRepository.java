package ru.practicum.ewm.events;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewm.categories.Category;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByInitiatorIdInAndStateInAndCategoryInAndEventDateAfterAndEventDateBefore(
            int[] users, State[] states, List<Category> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageRequest);

    List<Event> findAllByInitiatorId(int initiatorId, PageRequest pageRequest);

    List<Event> findAllByIdIn(List<Integer> eventIds);
}

