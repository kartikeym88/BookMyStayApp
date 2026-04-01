import java.util.*;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates queue-based request handling using FIFO principle.
 *
 * @author Kartikey
 * @version 5.1
 */

// ---------------------- RESERVATION MODEL ----------------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}

// ---------------------- BOOKING QUEUE ----------------------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):\n");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.display();
        }
    }

    // Peek next request (no removal)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        String appName = "Book My Stay";
        String version = "v5.1";

        System.out.println("=======================================");
        System.out.println("   " + appName + " " + version);
        System.out.println("=======================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        // Display queue
        bookingQueue.displayQueue();

        // Peek next request
        Reservation next = bookingQueue.peekNext();
        if (next != null) {
            System.out.println("\nNext request to process:");
            next.display();
        }

        System.out.println("\nNo inventory changes performed (intake stage only).");
    }
}