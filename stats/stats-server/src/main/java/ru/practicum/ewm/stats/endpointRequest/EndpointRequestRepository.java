package ru.practicum.ewm.stats.endpointRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EndpointRequestRepository extends JpaRepository<EndpointRequest, Long> {

    @Query("select new ru.practicum.ewm.stats.endpointRequest.EndpointRequestShort(en.appName, en.uri, count(en.ip)) " +
            "from EndpointRequest as en " +
            "where en.uri IN :uris AND " +
            "en.createdDate BETWEEN :start AND :end " +
            "group by en.uri, en.appName " +
            "order by count(en.uri) desc")
    List<EndpointRequestShort> findEventsByUris(@Param("uris") Collection<String> uris,
                                                @Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.endpointRequest.EndpointRequestShort(en.appName, en.uri, count(distinct en.ip)) " +
            "from EndpointRequest as en " +
            "where en.uri IN :uris AND " +
            "en.createdDate BETWEEN :start AND :end " +
            "group by en.uri, en.appName " +
            "order by count(en.uri) desc")
    List<EndpointRequestShort> findEventsByUrisDistinct(@Param("uris") Collection<String> uris,
                                                        @Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.endpointRequest.EndpointRequestShort(en.appName, en.uri, count(en.ip)) " +
            "from EndpointRequest as en " +
            "where en.createdDate BETWEEN :start AND :end " +
            "group by en.uri, en.appName " +
            "order by count(en.uri) desc")
    List<EndpointRequestShort> findAllEventsWithoutUris(@Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end);
}

