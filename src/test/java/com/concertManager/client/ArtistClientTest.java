package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Artist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistClientTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    @InjectMocks
    private ArtistClient artistClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Mocks are injected automatically via @InjectMocks
    }

    @Test
    void testGetArtist_Success() throws Exception {
        // Prepare a sample Artist as JSON (server would return this)
        Artist expectedArtist = new Artist();
        expectedArtist.setId(1L);
        expectedArtist.setStageName("Test Artist");
        expectedArtist.setGenre("Rock");

        String jsonResponse = objectMapper.writeValueAsString(expectedArtist);

        // When HTTP client is called, return the JSON response
        when(httpClientWrapper.doGet("/artists/1")).thenReturn(jsonResponse);

        // Call the client
        Artist result = artistClient.getArtist(1L);

        // Assert
        assertNotNull(result, "Artist should not be null");
        assertEquals(1L, result.getId());
        assertEquals("Test Artist", result.getStageName());
        assertEquals("Rock", result.getGenre());

        verify(httpClientWrapper).doGet("/artists/1");
    }
}
