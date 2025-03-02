package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TicketClient {

    private final HttpClientWrapper httpClientWrapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TicketClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Ticket getTicket(Long id) throws IOException {
        String path = "/tickets/" + id;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, Ticket.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ticket> getTicketsForEvent(Long eventId) throws IOException {
        String path = "/tickets/event/" + eventId;
        String response = httpClientWrapper.doGet(path);
        if (response == null) return List.of();
        try {
            Ticket[] tickets = objectMapper.readValue(response, Ticket[].class);
            return Arrays.asList(tickets);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Ticket createTicket(Ticket ticket) {
        String path = "/tickets";
        String response = httpClientWrapper.doPost(path, ticket);
        if (response == null) return null;
        try {
            return objectMapper.readValue(response, Ticket.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}