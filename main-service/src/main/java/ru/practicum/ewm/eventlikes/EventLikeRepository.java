package ru.practicum.ewm.eventlikes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.events.Event;

import java.util.List;

public interface EventLikeRepository extends JpaRepository<EventLike, Integer> {

    @Query("select new ru.practicum.ewm.eventlikes.EventLikeResponse(el.event.title, count(el.event.id)) " +
            "from EventLike as el " +
            "group by el.event.title " +
            "order by count(el.id) desc")
    List<EventLikeResponse> findPopularEventsByLikes();

    @Query("select el.event " +
            "from EventLike as el " +
            "where el.user.id = ?1")
    List<Event> findEventsByUserLikes(int userId);
}
