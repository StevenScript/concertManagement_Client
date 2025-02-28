package com.concertManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Event event;
    private String seatNumber;
    private String ticketType;
    private String buyerName;
}
