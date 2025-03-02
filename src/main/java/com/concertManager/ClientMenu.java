package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.client.EventClient;
import com.concertManager.client.TicketClient;
import com.concertManager.model.Artist;
import com.concertManager.model.Event;
import com.concertManager.model.Ticket;

import java.io.IOException;
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

    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Select an action:");
            System.out.println("1) Get artist by ID");
            System.out.println("2) List upcoming events");
            System.out.println("3) Search events by artist name");
            System.out.println("4) View available tickets for an event");
            System.out.println("5) Purchase tickets for an event");
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
                case "2": {
                    List<Event> events = eventClient.getUpcomingEvents();
                    if (events.isEmpty()) {
                        System.out.println("No upcoming events found.");
                    } else {
                        for (Event e : events) {
                            System.out.println(e.getEventDate() + " - $" + e.getTicketPrice());
                        }
                    }
                    break;
                }
                case "3": {
                    System.out.print("Enter artist ID: ");
                    String artistIdInput = scanner.nextLine().trim();
                    if (artistIdInput.isEmpty()) {
                        System.out.println("Invalid input. Artist ID cannot be blank.");
                        break; // or prompt again if desired
                    }
                    try {
                        Long artistId = Long.parseLong(artistIdInput);

                        List<Event> events = eventClient.getEventsByArtistId(artistId);
                        if (events.isEmpty()) {
                            System.out.println("No events found for artist ID: " + artistId);
                        } else {
                            for (Event e : events) {
                                System.out.println(e.getEventDate() + " - $" + e.getTicketPrice());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format. Please enter a valid number.");
                    } catch (IOException e) {
                        System.out.println("Error fetching events: " + e.getMessage());
                    }
                    break;
            }
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
                case "5":
                    System.out.print("Purchase Ticket ");
                    try {
                        System.out.print("Enter event ID: ");
                        Long eventId = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter seat number: ");
                        String seatNumber = scanner.nextLine();
                        System.out.print("Enter ticket type (e.g., VIP, GA): ");
                        String ticketType = scanner.nextLine();
                        System.out.print("Enter buyer name: ");
                        String buyerName = scanner.nextLine();

                        Event event = new Event();
                        event.setId(eventId);

                        Ticket ticketToPurchase = new Ticket();
                        ticketToPurchase.setEvent(event);
                        ticketToPurchase.setSeatNumber(seatNumber);
                        ticketToPurchase.setTicketType(ticketType);
                        ticketToPurchase.setBuyerName(buyerName);

                        Ticket purchasedTicket = ticketClient.createTicket(ticketToPurchase);
                        if (purchasedTicket == null) {
                            System.out.println("Ticket purchase failed.");
                        } else {
                            System.out.println("Ticket purchased successfully! Ticket ID: " + purchasedTicket.getId());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for event ID.");
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

