## Author - Steven Norris
## Date - February 28th, 2025

# Concert Management Client

## Overview
The **Concert Management Client** is a Java-based application designed to interact with a Spring Boot REST API that manages concerts, artists, events, tickets, and venues. This client provides a command-line interface (CLI) for users to retrieve and manage concert-related data via HTTP requests.

## Features
- Retrieve artist details by ID
- List upcoming events
- Search for events by artist ID
- View available tickets for a specific event
- Purchase tickets for an event

## Technologies Used
- Java 17
- Apache HttpClient (for HTTP requests)
- Jackson Databind (for JSON parsing)
- Lombok (for cleaner model classes)
- Scanner (for user input handling)

## Project Structure
```
concertManager/
│── src/
│   ├── main/
│   │   ├── java/com/concertManager/
│   │   │   ├── client/        # Clients for interacting with the API
│   │   │   ├── model/         # Data models (Artist, Event, Ticket, Venue)
│   │   │   ├── HttpClientWrapper.java  # Handles API communication
│   │   │   ├── ClientMenu.java  # CLI for user interaction
│   │   │   ├── Main.java  # Entry point of the application
│── pom.xml  # Project dependencies
│── README.md  # Project documentation
```

## Installation & Setup
### Prerequisites
- Java 17 or later
- Apache Maven
- Spring Boot API running at `http://localhost:8080`

### Steps to Run the Client
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/concert-management-client.git
   cd concert-management-client
   ```
2. Compile the project using Maven:
   ```sh
   mvn clean compile
   ```
3. Run the application:
   ```sh
   mvn exec:java -Dexec.mainClass="com.concertManager.Main"
   ```

## Usage
Upon running the application, a menu will be displayed with options for managing artists, events, and tickets. Users can enter the corresponding number to execute different operations.

## API Endpoints Used
- `GET /artists/{id}` - Fetch an artist by ID
- `GET /events/upcoming` - Retrieve upcoming events
- `GET /events/artist/{artistId}` - Get events for a specific artist
- `GET /tickets/event/{eventId}` - Get available tickets for an event
- `POST /tickets` - Purchase a ticket

Due to being past deadline, I was unable to fully implent all server side features.
While option 3 doesnt work yet.

