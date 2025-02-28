package com.concertManager.client;


import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventClientTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    @InjectMocks
    private EventClient eventClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetEvent_Success() throws Exception {
        Event expectedEvent = new Event();
        expectedEvent.setId(7L);
        expectedEvent.setEventDate(LocalDate.of(2025, 12, 25));
        expectedEvent.setTicketPrice(75.50);
        expectedEvent.setAvailableTickets(150);

        String jsonResponse = objectMapper.writeValueAsString(expectedEvent);
        when(httpClientWrapper.doGet("/events/7")).thenReturn(jsonResponse);

        Event result = eventClient.getEvent(7L);
        assertNotNull(result);
        assertEquals(7L, result.getId());
        assertEquals(LocalDate.of(2025, 12, 25), result.getEventDate());
        assertEquals(75.50, result.getTicketPrice());
        assertEquals(150, result.getAvailableTickets());

        verify(httpClientWrapper).doGet("/events/7");
    }
}
