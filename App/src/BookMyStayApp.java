import java.util.*;

/**
 * Book My Stay Application
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation, rollback using Stack,
 * and inventory restoration.
 *
 * @author Kartikey
 * @version 10.1
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

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

// ---------------------- INVENTORY ----------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    public void decreaseRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// ---------------------- BOOKING SERVICE ----------------------
class BookingService {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Map<String, String> allocatedRoomIds = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Confirm booking (simplified)
    public void confirmBooking(Reservation r) {

        String roomId = "ROOM_" + UUID.randomUUID().toString().substring(0, 5);

        confirmedBookings.put(r.getReservationId(), r);
        allocatedRoomIds.put(r.getReservationId(), roomId);

        inventory.decreaseRoom(r.getRoomType());

        System.out.println("Booking confirmed for " + r.getGuestName() +
                " | Room ID: " + roomId);
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation FAILED: Reservation not found.");
            return;
        }

        Reservation r = confirmedBookings.get(reservationId);

        // Push to rollback stack
        String roomId = allocatedRoomIds.get(reservationId);
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increaseRoom(r.getRoomType());

        // Remove booking
        confirmedBookings.remove(reservationId);
        allocatedRoomIds.remove(reservationId);

        System.out.println("Cancellation SUCCESS for " + reservationId +
                " | Released Room ID: " + roomId);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Rooms): " + rollbackStack);
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay v10.1 =====");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Create reservations
        Reservation r1 = new Reservation("R101", "Alice", "Single Room");
        Reservation r2 = new Reservation("R102", "Bob", "Double Room");

        // Confirm bookings
        service.confirmBooking(r1);
        service.confirmBooking(r2);

        inventory.displayInventory();

        // Cancel booking
        service.cancelBooking("R101");

        // Attempt invalid cancellation
        service.cancelBooking("R999");

        inventory.displayInventory();
        service.displayRollbackStack();

        System.out.println("\nSystem state restored safely after cancellation.");
    }
}