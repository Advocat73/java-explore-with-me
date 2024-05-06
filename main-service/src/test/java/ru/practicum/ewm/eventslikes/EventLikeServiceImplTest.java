package ru.practicum.ewm.eventslikes;

import org.junit.jupiter.api.Test;
import ru.practicum.ewm.categories.Category;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.events.State;
import ru.practicum.ewm.users.User;

import java.time.LocalDateTime;


public class EventLikeServiceImplTest {

    @Test
    void findPopularEventsByLikes(){

    }

    /*private Event createEvent(int i){
        Event event = new Event();
        event.setAnnotation("AnnotationAnnotationAnnotation" + i);
        Category category = new Category();
        category.setName("Category" + i);
        event.setCategory(categoryRepository.save(category));
        event.setDescription("DescriptionDescriptionDescription");
        User user = createUser(i);
        userRepository.save(user);
        event.setInitiator(user);
        event.setTitle("Title");
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
    }*/
}
