package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.client.TicketClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;
import com.concertManager.model.Ticket;

import java.util.List;
import java.util.Scanner;
public class ClientMenu {

    private final ArtistClient artistClient;
    private final EventClient eventClient;
    private final TicketClient ticketClient;
    // TODO: add VenueClient


    public ClientMenu(ArtistClient artistClient, Object venueClient, EventClient eventClient, TicketClient ticketClient) {
        this.artistClient = artistClient;
        this.eventClient = eventClient;
        this.ticketClient = ticketClient;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Select an action:");
            System.out.println("1) Get artist by ID");
            System.out.println("2) List upcoming events");
            System.out.println("3) Search events by artist name");
            System.out.println("4) View available tickets for an event");
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
                case "3":
                    System.out.print("Enter artist name to search for events: ");
                    String artistName = scanner.nextLine();
                    List<Event> searchResults = eventClient.searchEventsByArtistName(artistName);
                    if (searchResults.isEmpty()) {
                        System.out.println("No events found for artist: " + artistName);
                    } else {
                        System.out.println("Events for " + artistName + ":");
                        for (Event e : searchResults) {
                            System.out.println("Event ID: " + e.getId() + ", Date: " + e.getEventDate());
                        }
                    }
                    break;
                case "4":
                    System.out.print("Enter event ID to view available tickets: ");
                    try {
                        Long eventId = Long.parseLong(scanner.nextLine());
                        List<Ticket> tickets = ticketClient.getTicketsForEvent(eventId);
                        if (tickets.isEmpty()) {
                            System.out.println("No tickets available for event " + eventId);
                        } else {
                            System.out.println("Available Tickets for event " + eventId + ":");
                            for (Ticket t : tickets) {
                                System.out.println("Ticket ID: " + t.getId() + ", Seat: " + t.getSeatNumber() +
                                        ", Type: " + t.getTicketType());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid event ID.");
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

