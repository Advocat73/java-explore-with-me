package ru.practicum.ewm.eventlikes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.events.Event;
import ru.practicum.ewm.requests.UserRequest;
import ru.practicum.ewm.users.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventLikeRepository extends JpaRepository<EventLike, Integer> {
   /* @Query("select el.event " +
            "from EventLike as el " +
            "group by el.event.id " +
            "order by count(el.event.id) desc ")
    List<Event> findPopularEventsByLikes();*/

    @Query("select new ru.practicum.ewm.eventlikes.EventLikeResponse(el.event.title, count(el.event.id)) " +
            "from EventLike as el " +
            "group by el.event.title " +
            "order by count(el.id) desc")
    List<EventLikeResponse> findPopularEventsByLikes();

    @Query( "select el.event " +
            "from EventLike as el " +
            "where el.user.id = ?1")
    List<Event> findEventsByUserLikes(int userId);

    /*@Query(value = "SELECT * " +
            "from events as ev " +
            "JOIN event_likes AS el ON ev.id = el.event_id " +
            "GROUP by el.id " *//*+
            "order by count(el.event_id) desc"*//*,
            nativeQuery = true)
    List<Event> findPopularEventsByLikes();*/
}
