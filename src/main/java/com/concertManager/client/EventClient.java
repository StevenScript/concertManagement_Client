package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EventClient {

    private final HttpClientWrapper httpClientWrapper;
    private final ObjectMapper objectMapper;

    public EventClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Event getEvent(Long id) {
        String path = "/events/" + id;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, Event.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Event[] getUpcomingEvents() throws IOException {
        String response = httpClientWrapper.doGet("/events/upcoming");
        if (response.contains("error")) {
            throw new IOException("Failed to fetch events: " + response);
        }
        return objectMapper.readValue(response, Event[].class);
    }

    public List<Event> searchEventsByArtistName(String artistName) {
        String path = "/events/search?artistName=" + artistName;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return List.of();
        }
        try {
            Event[] events = objectMapper.readValue(response, Event[].class);
            return Arrays.asList(events);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
