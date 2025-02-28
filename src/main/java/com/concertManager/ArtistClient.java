package com.concertManager;

import com.concertManager.model.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
public class ArtistClient {

    private final HttpClientWrapper httpClientWrapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ArtistClient(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    public Artist getArtist(Long id) {
        String path = "/artists/" + id;
        String response = httpClientWrapper.doGet(path);
        if (response == null) {
            return null;  // Or throw an exception
        }
        try {
            return objectMapper.readValue(response, Artist.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
