package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.client.TicketClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;
import com.concertManager.model.Ticket;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
public class ClientMenuTest {

    @Test
    void testMenuOptionGetArtist() throws IOException {
        // Simulate user input:
        // "1" to select Get Artist, then "1" for artist ID, then "0" to exit.
        String simulatedInput = "1\n1\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        // Create a mock ArtistClient
        // stub the getArtist method
        ArtistClient mockArtistClient = mock(ArtistClient.class);
        Artist mockArtist = new Artist();
        mockArtist.setId(1L);
        mockArtist.setStageName("Test Artist");
        mockArtist.setGenre("Rock");
        when(mockArtistClient.getArtist(1L)).thenReturn(mockArtist);

        // Instantiate ClientMenu with the mock client.
        ClientMenu menu = new ClientMenu(mockArtistClient, null, null, null);
        menu.run();

        // Verify that the mockArtistClient.getArtist was called with ID 1.
        verify(mockArtistClient).getArtist(1L);

        // Restore the original System.in
        System.setIn(originalIn);
    }


    @Test
    void testMenuOptionListUpcomingEvents() throws IOException {
        // Simulate user input: "2" (option) then "0" (exit)
        String simulatedInput = "2\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        // Create a mock EventClient
        EventClient mockEventClient = mock(EventClient.class);
        List<Event> upcomingEvents = new ArrayList<>();
        Event e1 = new Event();
        e1.setId(101L);
        e1.setEventDate(LocalDate.of(2025, 6, 1));
        upcomingEvents.add(e1);

        when(mockEventClient.getUpcomingEvents()).thenReturn(upcomingEvents);

        // We'll pass null for other clients for now
        ClientMenu menu = new ClientMenu(null, null, mockEventClient, null);

        // Run the menu
        menu.run();

        // Verify that getUpcomingEvents() was called
        verify(mockEventClient).getUpcomingEvents();

        // Restore original System.in
        System.setIn(originalIn);
    }

    @Test
    void testMenuOptionViewAvailableTickets() throws IOException {
        // Simulate user input
        String simulatedInput = "4\n50\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        // Create a mock TicketClient with getTicketsForEvent implemented.
        TicketClient mockTicketClient = mock(TicketClient.class);
        // Prepare an empty list or some sample tickets (for testing, let's return an empty list).
        List<Ticket> sampleTickets = List.of(); // or List.of(sampleTicket1, sampleTicket2)
        when(mockTicketClient.getTicketsForEvent(50L)).thenReturn(sampleTickets);

        // Instantiate ClientMenu with the mock TicketClient.
       // if not, pass null)
        ClientMenu menu = new ClientMenu(null, null, null, mockTicketClient);

        // Run the menu, which should process the simulated input.
        menu.run();

        // Verify that getTicketsForEvent was called with the correct event ID.
        verify(mockTicketClient).getTicketsForEvent(50L);

        // Restore original System.in
        System.setIn(originalIn);
    }

    @Test
    void testMenuOptionPurchaseTicket() throws IOException {
        String simulatedInput = "5\n60\nA10\nVIP\nJohn Doe\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        TicketClient mockTicketClient = mock(TicketClient.class);

        when(mockTicketClient.createTicket(any())).thenAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            if (arg instanceof com.concertManager.model.Ticket) {
                com.concertManager.model.Ticket t = (com.concertManager.model.Ticket) arg;
                t.setId(200L);
                return t;
            }
            return null;
        });

        ClientMenu menu = new ClientMenu(null, null, null, mockTicketClient);

        menu.run();

        verify(mockTicketClient).createTicket(argThat(ticket ->
                ticket.getEvent() != null &&
                        ticket.getEvent().getId().equals(60L) &&
                        "A10".equals(ticket.getSeatNumber()) &&
                        "VIP".equals(ticket.getTicketType()) &&
                        "John Doe".equals(ticket.getBuyerName())
        ));

        System.setIn(originalIn);
    }

}
