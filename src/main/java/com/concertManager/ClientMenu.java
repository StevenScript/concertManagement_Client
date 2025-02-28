package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;

import java.util.List;
import java.util.Scanner;
public class ClientMenu {

    private final ArtistClient artistClient;
    private final EventClient eventClient;
    // TODO: add VenueClient, EventClient, TicketClient.


    public ClientMenu(ArtistClient artistClient, Object venueClient, EventClient eventClient, Object ticketClient) {
        this.artistClient = artistClient;
        this.eventClient = eventClient;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Select an action:");
            System.out.println("1) Get artist by ID");
            System.out.println("2) List upcoming events");
            System.out.println("0) Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter artist ID: ");
                    String idInput = scanner.nextLine();
                    try {
                        Long id = Long.parseLong(idInput);
                        Artist artist = artistClient.getArtist(id);
                        if (artist == null) {
                            System.out.println("Artist not found.");
                        } else {
                            System.out.println("Artist found: " + artist.getStageName() + " (" + artist.getGenre() + ")");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format.");
                    }
                    break;
                case "2":
                    // New code for “List upcoming events”
                    List<Event> upcoming = eventClient.getUpcomingEvents();
                    if (upcoming.isEmpty()) {
                        System.out.println("No upcoming events found.");
                    } else {
                        System.out.println("Upcoming Events:");
                        for (Event e : upcoming) {
                            System.out.println("Event ID: " + e.getId() + ", Date: " + e.getEventDate() +
                                    ", Price: " + e.getTicketPrice() + ", Avail: " + e.getAvailableTickets());
                        }
                    }
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
        scanner.close();
    }
}

