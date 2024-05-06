package ru.practicum.ewm.eventslikes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.categories.CategoryRepository;
import ru.practicum.ewm.eventlikes.EventLike;
import ru.practicum.ewm.eventlikes.EventLikeRepository;
import ru.practicum.ewm.eventlikes.EventLikeResponse;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.EventRepository;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.users.User;
import ru.practicum.ewm.users.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventLikeRepositoryTest {
    @Autowired
    private EventLikeRepository eventLikeRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void addNewEventLikeWithoutSaveEventAndUser() {
        Event event = createEvent(1);
        User eventCreator = createUser(100);
        EventLike eventLike = new EventLike(event, eventCreator);
        assertThrows(Exception.class,
                () -> eventLikeRepository.save(eventLike));
    }

    @Test
    public void addNewEventLikeWithSaveEventAndWithoutSaveUser() {
        Event event = createEvent(1);
        User eventCreator = createUser(100);
        eventRepository.save(event);
        EventLike eventLike = new EventLike(event, eventCreator);
        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> eventLikeRepository.save(eventLike));
    }

    @Test
    public void addNewEventLikeWithSaveEventAndWithSaveUser() {
        Event event = createEvent(1);
        User eventCreator = createUser(100);
        eventRepository.save(event);
        userRepository.save(eventCreator);
        EventLike eventLike = new EventLike(event, eventCreator);
        EventLike eventLikeSaved = eventLikeRepository.save(eventLike);
        assertEquals(eventLikeSaved.getId(), 1);
    }

    @Test
    public void addNewEventLikeWithSameEventAndSameUser() {
        Event event = createEvent(1);
        User eventCreator = createUser(100);
        eventRepository.save(event);
        userRepository.save(eventCreator);
        EventLike eventLike = new EventLike(event, eventCreator);
        eventLikeRepository.save(eventLike);
        EventLike eventLikeSecond = new EventLike(event, eventCreator);
        assertThrows(DataIntegrityViolationException.class,
                () -> eventLikeRepository.save(eventLikeSecond));
    }

    @Test
    public void deleteExistAndNoExistEventLike() {
        Event event = createEvent(1);
        User eventCreator = createUser(100);
        eventRepository.save(event);
        userRepository.save(eventCreator);
        EventLike eventLike = new EventLike(event, eventCreator);
        EventLike eventLikeSaved = eventLikeRepository.save(eventLike);
        assertEquals(eventLikeSaved.getId(), 1);
        eventLikeRepository.delete(eventLikeSaved);
        EventLike eventLikeSecond = eventLikeRepository.findById(eventLikeSaved.getId()).orElse(null);
        assertNull(eventLikeSecond);
    }

    @Test
    public void findPopularEventsByLikes() {
        User[] eventLikeCreators = new User[3];
        Event[] events = new Event[3];
        for (int i = 0; i < 3; i++) {
            eventLikeCreators[i] = createUser(100 + i);
            userRepository.save(eventLikeCreators[i]);
            events[i] = createEvent(i);
            eventRepository.save(events[i]);
        }
        eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[0]));
        eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[1]));
        eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[2]));
        eventLikeRepository.save(new EventLike(events[1], eventLikeCreators[0]));
        eventLikeRepository.save(new EventLike(events[1], eventLikeCreators[1]));
        eventLikeRepository.save(new EventLike(events[2], eventLikeCreators[0]));
        List<EventLikeResponse> eventLst = eventLikeRepository.findPopularEventsByLikes();
        assertEquals(eventLst.size(), 3);
        assertEquals(eventLst.get(0).getEventTitle(), events[0].getTitle());
    }

    @Test
    public void findEventsByUserLikes() {
        User[] eventLikeCreators = new User[3];
        Event[] events = new Event[3];
        for (int i = 0; i < 3; i++) {
            eventLikeCreators[i] = createUser(100 + i);
            userRepository.save(eventLikeCreators[i]);
            events[i] = createEvent(i);
            eventRepository.save(events[i]);
        }
        EventLike eventLike1 = eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[0]));
        EventLike eventLike2 = eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[1]));
        EventLike eventLike3 = eventLikeRepository.save(new EventLike(events[0], eventLikeCreators[2]));
        EventLike eventLike4 = eventLikeRepository.save(new EventLike(events[1], eventLikeCreators[0]));
        EventLike eventLike5 = eventLikeRepository.save(new EventLike(events[1], eventLikeCreators[1]));
        EventLike eventLike6 = eventLikeRepository.save(new EventLike(events[2], eventLikeCreators[0]));
        List<Event> eventLst = eventLikeRepository.findEventsByUserLikes(eventLikeCreators[0].getId());
        assertEquals(eventLst.size(), 3);
    }

    private Event createEvent(int i) {
        Event event = new Event();
        event.setAnnotation("AnnotationAnnotationAnnotation" + i);
        Category category = new Category();
        category.setName("Category" + i);
        event.setCategory(categoryRepository.save(category));
        event.setDescription("DescriptionDescriptionDescription");
        User user = createUser(i);
        userRepository.save(user);
        event.setInitiator(user);
        event.setTitle("Title" + i);
        event.setEventDate(LocalDateTime.now().plusDays(3));
        event.setPaid(true);
        event.setRequestModeration(true);
        event.setState(State.PUBLISHED);
        return event;
    }

    private User createUser(int i) {
        User user = new User();
        user.setName("User" + i);
        user.setEmail("user" + i + "@mail.ru");
        return user;
    }
}
