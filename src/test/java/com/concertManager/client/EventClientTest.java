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
import java.util.List;

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

    @Test
    void testGetUpcomingEvents_Success() throws Exception {
        // Server returns an array of Events
        Event event1 = new Event();
        event1.setId(101L);
        event1.setEventDate(LocalDate.of(2025, 5, 10));
        event1.setTicketPrice(50.0);
        event1.setAvailableTickets(100);

        Event event2 = new Event();
        event2.setId(102L);
        event2.setEventDate(LocalDate.of(2025, 7, 1));
        event2.setTicketPrice(75.0);
        event2.setAvailableTickets(200);

        // Convert to JSON array
        Event[] upcomingArray = { event1, event2 };
        String jsonResponse = objectMapper.writeValueAsString(upcomingArray);

        // When doGet is called with "/events/upcoming", return that JSON
        when(httpClientWrapper.doGet("/events/upcoming")).thenReturn(jsonResponse);

        // Call getUpcomingEvents
        List<Event> result = eventClient.getUpcomingEvents();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getId());
        assertEquals(102L, result.get(1).getId());

        verify(httpClientWrapper).doGet("/events/upcoming");
    }

    @Test
    void testSearchEventsByArtistName_Success() throws Exception {
        // Prepare two sample Event objects
        Event event1 = new Event();
        event1.setId(201L);
        event1.setEventDate(LocalDate.of(2025, 8, 15));
        event1.setTicketPrice(60.0);
        event1.setAvailableTickets(150);

        Event event2 = new Event();
        event2.setId(202L);
        event2.setEventDate(LocalDate.of(2025, 9, 1));
        event2.setTicketPrice(65.0);
        event2.setAvailableTickets(100);

        // Create an array of events and convert it to JSON using the ObjectMapper.
        Event[] eventsArray = { event1, event2 };
        String jsonResponse = objectMapper.writeValueAsString(eventsArray);

        // Stub the HttpClientWrapper: when search is called, return our JSON.
        when(httpClientWrapper.doGet("/events/search?artistName=The Testers")).thenReturn(jsonResponse);

        // Call the new method on EventClient.
        List<Event> result = eventClient.searchEventsByArtistName("The Testers");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(201L, result.get(0).getId());
        assertEquals(202L, result.get(1).getId());

        verify(httpClientWrapper).doGet("/events/search?artistName=The Testers");
    }
}
