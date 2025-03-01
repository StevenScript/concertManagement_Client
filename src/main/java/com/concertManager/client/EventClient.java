package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Event;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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

    public List<Event> getUpcomingEvents() {
        try {
            String jsonResponse = httpClientWrapper.doGet("/events/upcoming");

            // Directly parse the JSON array into a List<Event>
            List<Event> events = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<List<Event>>() {}
            );
            return events;

        } catch (Exception e) {
            System.err.println("Error fetching events: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Event[] getEventsByArtistId(Long artistId) throws IOException {
        String response = httpClientWrapper.doGet("/events/artist/" + artistId);
        return objectMapper.readValue(response, Event[].class);
    }

}
