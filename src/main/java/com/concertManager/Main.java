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
        // 1) Set the base URL for your server's REST API.
        String baseUrl = "http://localhost:8080";

        // 2) Create a real CloseableHttpClient.
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 3) Instantiate your HttpClientWrapper with the base URL + real httpClient.
        HttpClientWrapper httpClientWrapper = new HttpClientWrapper(baseUrl, httpClient);

        // 4) Create the resource clients.
        ArtistClient artistClient = new ArtistClient(httpClientWrapper);
        VenueClient venueClient = new VenueClient(httpClientWrapper);
        EventClient eventClient = new EventClient(httpClientWrapper);
        TicketClient ticketClient = new TicketClient(httpClientWrapper);

        // 5) Create the console menu, injecting the clients.
        ClientMenu menu = new ClientMenu(
                artistClient,
                venueClient,
                eventClient,
                ticketClient
        );

        // 6) Run the menu. This will prompt the user until they exit.
        menu.run();
    }
}