import java.util.*;

/**
 * Book My Stay Application
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates storing confirmed bookings and generating reports.
 *
 * @author Kartikey
 * @version 8.1
 */

// ---------------------- RESERVATION ----------------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// ---------------------- BOOKING HISTORY ----------------------
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// ---------------------- REPORT SERVICE ----------------------
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {
        System.out.println("\nBooking History:\n");

        for (Reservation r : history.getAllReservations()) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            String type = r.getRoomType();
            roomCount.put(type, roomCount.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nBooking Summary Report:\n");

        for (String type : roomCount.keySet()) {
            System.out.println(type + " -> " + roomCount.get(type) + " bookings");
        }
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay v8.1 =====");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from Use Case 6)
        history.addReservation(new Reservation("R101", "Alice", "Single Room"));
        history.addReservation(new Reservation("R102", "Bob", "Double Room"));
        history.addReservation(new Reservation("R103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("R104", "David", "Suite Room"));

        // Reporting
        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();
        reportService.generateSummary();

        System.out.println("\nReporting completed. No data modified.");
    }
}