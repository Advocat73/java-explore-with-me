package ru.practicum.ewm.stats.endpointRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoint_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String appName;
    private String uri;
    private String ip;
    private LocalDateTime createdDate;
}

