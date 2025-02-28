package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Venue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VenueClientTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    @InjectMocks
    private VenueClient venueClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Mocks will be injected automatically
    }

    @Test
    void testGetVenue_Success() throws Exception {
        // Create Venue object
        Venue expectedVenue = new Venue();
        expectedVenue.setId(5L);
        expectedVenue.setName("Grand Hall");
        expectedVenue.setLocation("City Center");
        expectedVenue.setCapacity(5000);

        // Convert to JSON using Jackson
        String jsonResponse = objectMapper.writeValueAsString(expectedVenue);

        // When doGet is called on the wrapper, return the JSON response
        when(httpClientWrapper.doGet("/venues/5")).thenReturn(jsonResponse);

        // Call the client method
        Venue result = venueClient.getVenue(5L);

        // Verify the result
        assertNotNull(result, "Resulting Venue should not be null");
        assertEquals(5L, result.getId());
        assertEquals("Grand Hall", result.getName());
        assertEquals("City Center", result.getLocation());
        assertEquals(5000, result.getCapacity());

        // Verify that the HTTP client was called with the correct path
        verify(httpClientWrapper).doGet("/venues/5");
    }
}
