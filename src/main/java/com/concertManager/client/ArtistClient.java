package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
public class ArtistClient {

    private final HttpClientWrapper httpClientWrapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ArtistClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public Artist getArtist(Long id) throws IOException {
        String path = "/artists/" + id;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return null;
        }
        try {
            return objectMapper.readValue(response, Artist.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
