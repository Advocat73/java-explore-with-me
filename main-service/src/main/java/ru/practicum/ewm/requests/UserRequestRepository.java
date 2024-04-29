package ru.practicum.ewm.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRequestRepository extends JpaRepository<UserRequest, Integer> {
    List<UserRequest> findAllByRequesterId(int requesterId);

    @Query(value = "SELECT * "
            + " from user_requests as ur"
            + " JOIN events AS ev ON ur.event_id = ev.id"
            + " where ur.event_id = ?1 AND ev.initiator_id = ?2"
            , nativeQuery = true)
    List<UserRequest> findAllByEventIdAndInitiatorId(int eventId, int requesterId);
}
