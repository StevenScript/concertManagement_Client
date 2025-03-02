package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.client.TicketClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;
import com.concertManager.model.Ticket;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ClientMenuTest {

    @Test
    void testMenuOptionGetArtist() throws IOException {
        String simulatedInput = "1\n1\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        ArtistClient mockArtistClient = mock(ArtistClient.class);
        Artist mockArtist = new Artist();
        mockArtist.setId(1L);
        mockArtist.setStageName("Test Artist");
        mockArtist.setGenre("Rock");
        when(mockArtistClient.getArtist(1L)).thenReturn(mockArtist);

        ClientMenu menu = new ClientMenu(mockArtistClient, null, null, null);
        menu.run();

        verify(mockArtistClient).getArtist(1L);

        System.setIn(originalIn);
    }


    @Test
    void testMenuOptionListUpcomingEvents() throws IOException {
        String simulatedInput = "2\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        EventClient mockEventClient = mock(EventClient.class);
        List<Event> upcomingEvents = new ArrayList<>();
        Event e1 = new Event();
        e1.setId(101L);
        e1.setEventDate(LocalDate.of(2025, 6, 1));
        upcomingEvents.add(e1);

        when(mockEventClient.getUpcomingEvents()).thenReturn(upcomingEvents);

        ClientMenu menu = new ClientMenu(null, null, mockEventClient, null);

        menu.run();

        verify(mockEventClient).getUpcomingEvents();

        System.setIn(originalIn);
    }

    @Test
    void testMenuOptionViewAvailableTickets() throws IOException {
        String simulatedInput = "4\n50\n0\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));

        TicketClient mockTicketClient = mock(TicketClient.class);
        List<Ticket> sampleTickets = List.of();
        when(mockTicketClient.getTicketsForEvent(50L)).thenReturn(sampleTickets);

        ClientMenu menu = new ClientMenu(null, null, null, mockTicketClient);

        menu.run();

        verify(mockTicketClient).getTicketsForEvent(50L);

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
