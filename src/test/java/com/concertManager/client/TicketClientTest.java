package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Event;
import com.concertManager.model.Ticket;
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
public class TicketClientTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    @InjectMocks
    private TicketClient ticketClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetTicket_Success() throws Exception {
        Event expectedEvent = new Event();
        expectedEvent.setId(5L);
        expectedEvent.setEventDate(LocalDate.of(2025, 12, 31));
        expectedEvent.setTicketPrice(100.0);
        expectedEvent.setAvailableTickets(200);

        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(3L);
        expectedTicket.setBuyerName("Charlie");
        expectedTicket.setTicketType("VIP");
        expectedTicket.setEvent(expectedEvent);

        String jsonResponse = objectMapper.writeValueAsString(expectedTicket);

        when(httpClientWrapper.doGet("/tickets/3")).thenReturn(jsonResponse);

        Ticket result = ticketClient.getTicket(3L);

        assertNotNull(result, "Ticket should not be null");
        assertEquals(3L, result.getId());
        assertEquals("Charlie", result.getBuyerName());
        assertEquals("VIP", result.getTicketType());
        assertNotNull(result.getEvent(), "The event field should not be null");
        assertEquals(5L, result.getEvent().getId());
        assertEquals(LocalDate.of(2025, 12, 31), result.getEvent().getEventDate());
        assertEquals(100.0, result.getEvent().getTicketPrice());
        assertEquals(200, result.getEvent().getAvailableTickets());

        verify(httpClientWrapper).doGet("/tickets/3");
    }

    @Test
    void testGetTicketsForEvent_Success() throws Exception {

        Ticket ticket1 = new Ticket();
        ticket1.setId(10L);
        ticket1.setSeatNumber("A1");
        ticket1.setTicketType("VIP");
        ticket1.setBuyerName("Alice");

        Ticket ticket2 = new Ticket();
        ticket2.setId(11L);
        ticket2.setSeatNumber("A2");
        ticket2.setTicketType("GA");
        ticket2.setBuyerName("Bob");


        Ticket[] ticketsArray = { ticket1, ticket2 };
        String jsonResponse = objectMapper.writeValueAsString(ticketsArray);

        when(httpClientWrapper.doGet("/tickets/event/50")).thenReturn(jsonResponse);

        List<Ticket> result = ticketClient.getTicketsForEvent(50L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals("A1", result.get(0).getSeatNumber());
        assertEquals(11L, result.get(1).getId());
        assertEquals("A2", result.get(1).getSeatNumber());

        verify(httpClientWrapper).doGet("/tickets/event/50");
    }

    @Test
    void testCreateTicket_Success() throws Exception {

        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setEvent(new com.concertManager.model.Event());
        ticketToCreate.getEvent().setId(60L);
        ticketToCreate.setSeatNumber("A10");
        ticketToCreate.setTicketType("VIP");
        ticketToCreate.setBuyerName("John Doe");

        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(200L);
        expectedTicket.setEvent(ticketToCreate.getEvent());
        expectedTicket.setSeatNumber("A10");
        expectedTicket.setTicketType("VIP");
        expectedTicket.setBuyerName("John Doe");

        String jsonResponse = objectMapper.writeValueAsString(expectedTicket);

        when(httpClientWrapper.doPost("/tickets", ticketToCreate))
                .thenReturn(jsonResponse);

        Ticket result = ticketClient.createTicket(ticketToCreate);

        assertNotNull(result);
        assertEquals(200L, result.getId());
        assertNotNull(result.getEvent());
        assertEquals(60L, result.getEvent().getId());
        assertEquals("A10", result.getSeatNumber());
        assertEquals("VIP", result.getTicketType());
        assertEquals("John Doe", result.getBuyerName());

        verify(httpClientWrapper).doPost("/tickets", ticketToCreate);
    }
}
