package ru.practicum.ewm.stats.endpointRequest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.stats.endpointRequestDto.EndpointRequestInDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EndpointRequestRepositoryTest {
    private static final int RECORD_COUNT = 20;
    private static final int EVENTS_MAX_COUNT = 3;
    private static final String ALL_EVENTS_URI_NAME = "/events";
    private static final String EVENT_1_URI_NAME = "/events/1";
    private static final String EVENT_2_URI_NAME = "/events/2";
    private static final String EVENT_3_URI_NAME = "/events/3";
    private static final String EVENT_URI_NAME_NOT_EXIST = "/events/eventNotExist";
    private static final List<String> allEventsUriName = List.of(
            ALL_EVENTS_URI_NAME,
            EVENT_1_URI_NAME,
            EVENT_2_URI_NAME,
            EVENT_3_URI_NAME,
            EVENT_URI_NAME_NOT_EXIST);

    @Autowired
    private EndpointRequestRepository endpointRequestRepository;

    private final EasyRandom generator = new EasyRandom();
    private final Random random = new Random();

    Integer eventsCounter = 0;
    Integer event1Counter = 0;
    Integer event2Counter = 0;
    Integer event3Counter = 0;
    List<Long> sortedCountHitsLst = new ArrayList<>();

    String[] dbRecordsUri = new String[RECORD_COUNT];
    LocalDateTime[] dbRecordsCreatedDate = new LocalDateTime[RECORD_COUNT];

    @BeforeEach
    void setUp() {
        fillDataDB();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void findEventsByUris() {
        List<EndpointRequestShort> lst = fillLocalDataAndDBResultList(
                allEventsUriName,
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(random.nextInt(RECORD_COUNT+1)));
        for (int i = 0; i < lst.size(); i++)
            assertEquals(lst.get(i).getHits(), sortedCountHitsLst.get(i));
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void findAllEventsWithoutUris() {
        List<EndpointRequestShort> lst = fillLocalDataAndDBResultList(
                LocalDateTime.now(),
                LocalDateTime.now().plusYears(random.nextInt(RECORD_COUNT+1)));
        for (int i = 0; i < lst.size(); i++)
            assertEquals(lst.get(i).getHits(), sortedCountHitsLst.get(i));
    }

    private void fillDataDB() {
        for (int i = 0; i < RECORD_COUNT; i++) {
            EndpointRequest en = createAndSaveEndpointRequest((long) i);
            dbRecordsUri[i] = en.getUri();
            dbRecordsCreatedDate[i] = en.getCreatedDate();
        }
    }

    private EndpointRequest createAndSaveEndpointRequest(Long dur) {
        return endpointRequestRepository.save(EndpointRequestMapper.fromEndpointRequestInDto(createEndpointRequestInDto(dur)));
    }

    private EndpointRequestInDto createEndpointRequestInDto(Long dur) {
        EndpointRequestInDto endpointRequestInDto = new EndpointRequestInDto();
        endpointRequestInDto.setApp("ewm-main-service");
        int n = random.nextInt(EVENTS_MAX_COUNT) + 1;
        String uri = (random.nextBoolean()) ? "/events" : "/events/" + n;
        endpointRequestInDto.setUri(uri);
        endpointRequestInDto.setIp("121.0.0.1");
        LocalDateTime createdDate = LocalDateTime.now().plusYears(dur);
        endpointRequestInDto.setTimestamp(createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return endpointRequestInDto;
    }

    private List<EndpointRequestShort> fillLocalDataAndDBResultList(List<String> uriList, LocalDateTime start, LocalDateTime end) {
        countResultInLocalVarsAndSortByHits(start, end);
        return endpointRequestRepository.findEventsByUris(uriList, start, end);
    }

    private List<EndpointRequestShort> fillLocalDataAndDBResultList(LocalDateTime start, LocalDateTime end) {
        countResultInLocalVarsAndSortByHits(start, end);
        return endpointRequestRepository.findAllEventsWithoutUris(start, end);
    }

    private void countResultInLocalVarsAndSortByHits(LocalDateTime start, LocalDateTime end) {
        for (int i = 0; i < RECORD_COUNT; i++) {
            if (dbRecordsCreatedDate[i].isAfter(start) && dbRecordsCreatedDate[i].isBefore(end)) {
                switch (dbRecordsUri[i]) {
                    case ALL_EVENTS_URI_NAME:
                        eventsCounter++;
                        break;
                    case EVENT_1_URI_NAME:
                        event1Counter++;
                        break;
                    case EVENT_2_URI_NAME:
                        event2Counter++;
                        break;
                    case EVENT_3_URI_NAME:
                        event3Counter++;
                        break;
                    default:
                        break;
                }
            }
        }
        sortLocalArrayHits();
    }

    private void sortLocalArrayHits() {
        sortedCountHitsLst.add((long) eventsCounter);
        sortedCountHitsLst.add((long) event1Counter);
        sortedCountHitsLst.add((long) event2Counter);
        sortedCountHitsLst.add((long) event3Counter);
        Collections.sort(sortedCountHitsLst);
        Collections.reverse(sortedCountHitsLst);
    }

    private void countResult(LocalDateTime start, LocalDateTime end, String[] uris) {
        for (int i = 0; i < RECORD_COUNT; i++) {
            switch (dbRecordsUri[i]) {
                case ALL_EVENTS_URI_NAME:
                    eventsCounter++;
                    break;
                case EVENT_1_URI_NAME:
                    event1Counter++;
                    break;
                case EVENT_2_URI_NAME:
                    event2Counter++;
                    break;
                case EVENT_3_URI_NAME:
                    event3Counter++;
                    break;
                default:
                    break;
            }
        }
        sortedCountHitsLst.add((long) eventsCounter);
        sortedCountHitsLst.add((long) event1Counter);
        sortedCountHitsLst.add((long) event2Counter);
        sortedCountHitsLst.add((long) event3Counter);
        Collections.sort(sortedCountHitsLst);
        Collections.reverse(sortedCountHitsLst);
    }

    private void countResult(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        for (int i = 0; i < RECORD_COUNT; i++) {
            switch (dbRecordsUri[i]) {
                case ALL_EVENTS_URI_NAME:
                    eventsCounter++;
                    break;
                case EVENT_1_URI_NAME:
                    event1Counter++;
                    break;
                case EVENT_2_URI_NAME:
                    event2Counter++;
                    break;
                case EVENT_3_URI_NAME:
                    event3Counter++;
                    break;
                default:
                    break;
            }
        }
        sortedCountHitsLst.add((long) eventsCounter);
        sortedCountHitsLst.add((long) event1Counter);
        sortedCountHitsLst.add((long) event2Counter);
        sortedCountHitsLst.add((long) event3Counter);
        Collections.sort(sortedCountHitsLst);
        Collections.reverse(sortedCountHitsLst);
    }

    private void countResult(LocalDateTime start, LocalDateTime end, boolean unique) {
        for (int i = 0; i < RECORD_COUNT; i++) {
            switch (dbRecordsUri[i]) {
                case ALL_EVENTS_URI_NAME:
                    eventsCounter++;
                    break;
                case EVENT_1_URI_NAME:
                    event1Counter++;
                    break;
                case EVENT_2_URI_NAME:
                    event2Counter++;
                    break;
                case EVENT_3_URI_NAME:
                    event3Counter++;
                    break;
                default:
                    break;
            }
        }
        sortedCountHitsLst.add((long) eventsCounter);
        sortedCountHitsLst.add((long) event1Counter);
        sortedCountHitsLst.add((long) event2Counter);
        sortedCountHitsLst.add((long) event3Counter);
        Collections.sort(sortedCountHitsLst);
        Collections.reverse(sortedCountHitsLst);
    }
}

