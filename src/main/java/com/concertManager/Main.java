package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.client.TicketClient;
import com.concertManager.client.VenueClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String baseUrl = "http://localhost:8080";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpClientWrapper httpClientWrapper = new HttpClientWrapper(baseUrl, httpClient);

        ArtistClient artistClient = new ArtistClient(httpClientWrapper);
        VenueClient venueClient = new VenueClient(httpClientWrapper);
        EventClient eventClient = new EventClient(httpClientWrapper);
        TicketClient ticketClient = new TicketClient(httpClientWrapper);

        ClientMenu menu = new ClientMenu(
                artistClient,
                venueClient,
                eventClient,
                ticketClient
        );

        menu.run();
    }
}