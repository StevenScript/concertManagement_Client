package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.model.Artist;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
}
