package com.concertManager;

import com.concertManager.client.ArtistClient;
import com.concertManager.model.Artist;

import java.util.Scanner;
public class ClientMenu {

    private final ArtistClient artistClient;
    // TODO: add VenueClient, EventClient, TicketClient.


    public ClientMenu(ArtistClient artistClient, Object venueClient, Object eventClient, Object ticketClient) {
        this.artistClient = artistClient;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Select an action:");
            System.out.println("1) Get artist by ID");
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

