package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Venue;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class VenueClient {

    private final HttpClientWrapper httpClientWrapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VenueClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    public Venue getVenue(Long id) {
        String path = "/venues/" + id;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return null;  // Alternatively, throw an exception
        }
        try {
            return objectMapper.readValue(response, Venue.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
