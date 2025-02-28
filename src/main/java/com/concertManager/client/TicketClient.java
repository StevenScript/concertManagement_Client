package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TicketClient {

    private final HttpClientWrapper httpClientWrapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TicketClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    public Ticket getTicket(Long id) {
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
}