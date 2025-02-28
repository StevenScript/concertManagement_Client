package com.concertManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private LocalDate eventDate;
    private Double ticketPrice;
    private Integer availableTickets;
    private Venue venue;
    private Set<Artist> artists = new HashSet<>();
}
