package com.concertManager.client;

import com.concertManager.HttpClientWrapper;
import com.concertManager.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketClientTest {

    @Mock
    private HttpClientWrapper httpClientWrapper;

    @InjectMocks
    private TicketClient ticketClient; // To be created

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Mocks are injected automatically
    }

    @Test
    void testGetTicket_Success() throws Exception {
        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(3L);
        expectedTicket.setBuyerName("Charlie");
        expectedTicket.setTicketType("VIP");
        // Note: For simplicity, we omit the event field

        String jsonResponse = objectMapper.writeValueAsString(expectedTicket);

        when(httpClientWrapper.doGet("/tickets/3")).thenReturn(jsonResponse);

        Ticket result = ticketClient.getTicket(3L);
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Charlie", result.getBuyerName());
        assertEquals("VIP", result.getTicketType());

        verify(httpClientWrapper).doGet("/tickets/3");
    }
}
