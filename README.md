# 🎶 Concert Management Client

**A Command-Line Interface (CLI) application for managing concerts, artists, venues, and ticket bookings by interacting with the Concert Management Server's RESTful API.**

---

## 📌 Overview

This project is a **Java-based CLI client** designed to interact with the [Concert Management Server](https://github.com/StevenScript/concertManagement_Server). The client allows users to perform various operations related to concerts, artists, venues, and ticket bookings by making HTTP requests to the server's RESTful API.

---

## 🎮 CLI Features & Options

The client provides several command-line options to manage and query concert-related data:

### **1. List Artists**

**Command:**
```sh
list-artists
```

**Description:**  
Retrieves and displays a list of all artists available in the system.

**Example:**
```sh
> list-artists
1. Taylor Swift - Genre: Pop
2. Ed Sheeran - Genre: Pop
3. The Weeknd - Genre: R&B
```

---

### **2. List Venues**

**Command:**
```sh
list-venues
```

**Description:**  
Retrieves and displays a list of all venues.

**Example:**
```sh
> list-venues
1. Madison Square Garden - Location: New York, NY - Capacity: 20,000
2. The O2 Arena - Location: London, UK - Capacity: 20,000
3. Sydney Opera House - Location: Sydney, Australia - Capacity: 5,738
```

---

### **3. List Concerts**

**Command:**
```sh
list-concerts
```

**Description:**  
Retrieves and displays a list of all concerts.

**Example:**
```sh
> list-concerts
1. Artist: Taylor Swift - Venue: Madison Square Garden - Date: 2025-06-15 - Ticket Price: $150
2. Artist: Ed Sheeran - Venue: The O2 Arena - Date: 2025-07-20 - Ticket Price: $120
```

---

### **4. List Tickets**

**Command:**
```sh
list-tickets
```

**Description:**  
Retrieves and displays a list of all tickets.

**Example:**
```sh
> list-tickets
1. Ticket ID: 101 - Concert: Taylor Swift at Madison Square Garden - Buyer: John Doe - Seat: A12 - Status: Booked
2. Ticket ID: 102 - Concert: Ed Sheeran at The O2 Arena - Buyer: Jane Smith - Seat: B15 - Status: Booked
```

---

### **5. Create Artist**

**Command:**
```sh
create-artist <name> <genre> <bio>
```

**Description:**  
Creates a new artist with the provided name, genre, and biography.

**Example:**
```sh
> create-artist "Adele" "Soul" "Award-winning British singer-songwriter"
Artist 'Adele' created successfully.
```

---

### **6. Create Venue**

**Command:**
```sh
create-venue <name> <location> <capacity>
```

**Description:**  
Creates a new venue with the provided name, location, and capacity.

**Example:**
```sh
> create-venue "Staples Center" "Los Angeles, CA" 19000
Venue 'Staples Center' created successfully.
```

---

### **7. Create Concert**

**Command:**
```sh
create-concert <artist_id> <venue_id> <date> <ticket_price>
```

**Description:**  
Schedules a new concert with the specified artist, venue, date, and ticket price.

**Example:**
```sh
> create-concert 1 2 2025-08-10 100
Concert for artist ID 1 at venue ID 2 on 2025-08-10 created successfully.
```

---

### **8. Book Ticket**

**Command:**
```sh
book-ticket <concert_id> <buyer_name> <seat_number>
```

**Description:**  
Books a ticket for the specified concert with the buyer's name and seat number.

**Example:**
```sh
> book-ticket 1 "Alice Johnson" "C22"
Ticket booked successfully for Alice Johnson, Seat C22.
```

---

### **9. Cancel Ticket**

**Command:**
```sh
cancel-ticket <ticket_id>
```

**Description:**  
Cancels the ticket with the given ticket ID.

**Example:**
```sh
> cancel-ticket 101
Ticket ID 101 has been canceled successfully.
```

---

## 🛠️ Setup & Installation

### 💾 Prerequisites

- **Java 17+**
- **Maven** (for dependency management)
- **Internet Connection** (to communicate with the server API)

---

### 🚀 Running the Client

#### 1️⃣ Clone the Repository

```sh
git clone https://github.com/StevenScript/concertManagement_Client.git
cd concertManagement_Client
```

#### 2️⃣ Build the Application

```sh
mvn clean install
```

#### 3️⃣ Run the Application

```sh
java -jar target/concertManagement_Client-1.0.0.jar
```

---

## 🤮 Testing

### 🧐 Unit Tests

To run the tests:

```sh
mvn test
```

---

## 🌐 Server Interaction

Ensure that the [Concert Management Server](https://github.com/StevenScript/concertManagement_Server) is running before using the client.

---

