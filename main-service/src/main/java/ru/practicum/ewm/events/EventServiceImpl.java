package ru.practicum.ewm.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.categories.CategoryRepository;
import ru.practicum.ewm.events.dto.*;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.ForbiddenUpdateException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.users.User;
import ru.practicum.ewm.users.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto addNewEvent(NewEventDto newEventDto, int userId) {
        log.info("EVENT_СЕРВИС: Отправлен запрос на добавление нового события - {}", newEventDto.getTitle());
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Нет категории с ID: " + newEventDto.getCategory()));
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с ID: " + userId));
        return EventMapper.toEventFullDto(eventRepository.save(EventMapper.fromNewEventDto(newEventDto, category, initiator)));
    }

    @Override
    public EventFullDto updateEventByAdmin(UpdateEventAdminRequest updateEvent, int eventId) {
        log.info("EVENT_СЕРВИС: Отправлен запрос админа на изменение данных события с Id: {}", eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с Id: " + eventId));
        if (event.getState() != State.PENDING)
            throw new ForbiddenUpdateException("Чтобы изменить данные о событии, его статус д.б.: PENDING, актуальный статус: "
                    + event.getState());
        Category category = null;
        if (updateEvent.getCategory() != null)
            category = categoryRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFoundException("Нет категории с Id: " + updateEvent.getCategory()));
        EventMapper.fromUpdateEventAdminRequest(updateEvent, event, category);
        log.info("EVENT_СЕРВИС: Статус события стал - {}", event.getState());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto updateEventByInitiator(UpdateEventUserRequest updateEvent, int initiatorId, int eventId) {
        log.info("EVENT_СЕРВИС: Отправлен запрос польхователя с Id: {} на изменение данных события с Id: {}",
                initiatorId, eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с Id: " + eventId));
        if (event.getState().equals(State.PUBLISHED))
            throw new ConflictException("Не доступно событие с Id: " + eventId + " для изменений");
        if (event.getInitiator().getId() != initiatorId)
            throw new NotFoundException("Не доступно событие с Id: " + eventId + " для изменений");
        Category category = null;
        if (updateEvent.getCategory() != null)
            category = categoryRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFoundException("Нет категории с Id: " + updateEvent.getCategory()));
        EventMapper.fromUpdateEventInitiatorRequest(updateEvent, event, category);
        log.info("EVENT_СЕРВИС: Статус события стал - {}", event.getState());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto findEvent(int requesterId, int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с Id: " + eventId));
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventFullDto> findEventsByParamByAdmin(int[] initiators, State[] states, int[] categories, String rangeStart, String rangeEnd, int from, int size) {
        log.info("EVENT_СЕРВИС: Отправлен запрос админа на получение данных о событиях по парамметрам");
        LocalDateTime start = (rangeStart == null) ? null : LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = (rangeEnd == null) ? null : LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (start != null && end != null && (end.equals(start) || end.isBefore(start)))
            throw new BadRequestException("Время окончания поиска раньше, чем время начала поиска");
        SearchEventFilter filter = SearchEventFilter.builder()
                .initiators((initiators == null) ? null : userRepository.findAllByIdIn(initiators))
                .categories((categories == null) ? null : categoryRepository.findAllByIdIn(categories))
                .rangeStart(start)
                .rangeEnd(end)
                .states((states == null) ? null : List.of(states))
                .build();
        return EventMapper.toEventFullDtoList(searchEventByParam(filter, createPageRequest(null, from, size)));
    }

    @Override
    public List<EventFullDto> findInitiatorEvents(int initiatorId, int from, int size) {
        log.info("EVENT_СЕРВИС: Отправлен запрос пользователя на получение данных о событиях, где он инициатор");
        List<Event> events = eventRepository.findAllByInitiatorId(initiatorId, createPageRequest(null, from, size));
        return EventMapper.toEventFullDtoList(events);
    }

    @Override
    public List<EventShortDto> findEventsByPublicRequest(String text, int[] categories, Boolean paid, String rangeStart,
                                                         String rangeEnd, Boolean onlyAvailable, String sort,
                                                         int from, int size, String ipAddress) {
        log.info("EVENT_СЕРВИС: Отправлен публичный запрос на получение данных о событиях по парамметрам");
        LocalDateTime start = (rangeStart == null) ? null : LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = (rangeEnd == null) ? null : LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (start != null && end != null && (end.equals(start) || end.isBefore(start)))
            throw new BadRequestException("Время окончания поиска раньше, чем время начала поиска");
        SearchEventFilter filter = SearchEventFilter.builder()
                .text(text)
                .categories((categories == null) ? null : categoryRepository.findAllByIdIn(categories))
                .paid(paid)
                .rangeStart(start)
                .rangeEnd(end)
                .onlyAvailable(onlyAvailable)
                .state(State.PUBLISHED)
                .build();
        List<Event> events = searchEventByParam(filter, createPageRequest(sort, from, size));
        log.info("EVENT_СЕРВИС: Найдено {} событий", events.size());
        return EventMapper.toEventShortDtoList(events);
    }

    @Override
    public EventFullDto findEventByPublicRequest(int eventId, String ipAddress) {
        log.info("EVENT_СЕРВИС: Отправлен публичный запрос на получение данных о событии с Id: {}", eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие с Id: " + eventId));
        if (event.getState() != State.PUBLISHED)
            throw new NotFoundException("Не найдено событие с Id: " + eventId);
        if (!event.getIpAddresses().contains(ipAddress)) {
            event.setViews(event.getViews() + 1);
            event.getIpAddresses().add(ipAddress);
            eventRepository.save(event);
        }
        return EventMapper.toEventFullDto(event);
    }

    private PageRequest createPageRequest(String sort, int from, int size) {
        Sort sortBy;
        if (sort == null)
            sortBy = Sort.by("Id").descending();
        else if (sort.equals("EVENT_DATE"))
            sortBy = Sort.by("eventDate").ascending();
        else if (sort.equals("VIEWS"))
            sortBy = Sort.by("eventDate").ascending();
        else sortBy = Sort.by("Id").descending();
        int page = (from < size) ? 0 : from / size;
        return PageRequest.of(page, size, sortBy);
    }

    private List<Event> searchEventByParam(SearchEventFilter filter, PageRequest pageRequest) {
        List<Specification<Event>> specifications = searchFilterToSpecifications(filter);
        Specification<Event> specification = specifications.stream().reduce((Specification::and)).orElse(null);
        if (specification != null)
            return eventRepository.findAll(specification, pageRequest).getContent();
        else return List.of();
    }

    private List<Specification<Event>> searchFilterToSpecifications(SearchEventFilter filter) {
        List<Specification<Event>> specifications = new ArrayList<>();

        specifications.add((filter.getInitiators() == null) ? null : initiatorIn(filter.getInitiators()));
        specifications.add((filter.getText() == null) ? null : byText(filter.getText()));
        specifications.add((filter.getCategories() == null) ? null : categoryIn(filter.getCategories()));
        specifications.add((filter.getPaid() == null) ? null : byPaid(filter.getPaid()));
        specifications.add((filter.getRangeStart() == null && filter.getRangeEnd() == null) ?
                eventDateAfterNow() : betweenRange(filter.getRangeStart(), filter.getRangeEnd()));
        specifications.add((filter.getOnlyAvailable() == null) ? null : byOnlyAvailable(filter.getOnlyAvailable()));
        specifications.add((filter.getState() == null) ? null : byState(filter.getState()));
        specifications.add((filter.getStates() == null) ? null : stateIn(filter.getStates()));

        return specifications.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Specification<Event> initiatorIn(List<User> initiators) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("initiator")).value(initiators);
    }

    private Specification<Event> byText(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), text.toLowerCase()),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), text.toLowerCase()));
    }

    private Specification<Event> categoryIn(List<Category> categories) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("category")).value(categories);
    }

    private Specification<Event> byPaid(Boolean paid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid);
    }

    private Specification<Event> eventDateAfterNow() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("eventDate"), LocalDateTime.now());
    }

    private Specification<Event> betweenRange(LocalDateTime start, LocalDateTime end) {
        if (start != null & end != null)
            return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("eventDate"), start, end);
        else if (start != null & end == null)
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), start);
        else
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), end);
    }

    private Specification<Event> byOnlyAvailable(Boolean onlyAvailable) {
        if (onlyAvailable)
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("confirmedRequests"), root.get("participantLimit"));
        else return null;
    }

    private Specification<Event> byState(State state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.equal(root.get("state"), state));
    }

    private Specification<Event> stateIn(List<State> states) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("state")).value(states);
    }
}