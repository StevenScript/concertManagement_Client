package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
public class ClientMenuTest {

    @Test
    void testMenuOptionGetArtist() {
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
    void testMenuOptionListUpcomingEvents() {
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
}
