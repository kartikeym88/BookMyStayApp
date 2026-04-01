import java.util.*;

/**
 * Book My Stay Application
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates input validation, custom exceptions,
 * and fail-fast system design.
 *
 * @author Kartikey
 * @version 9.1
 */

// ---------------------- CUSTOM EXCEPTION ----------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------------- RESERVATION ----------------------
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
}

// ---------------------- INVENTORY ----------------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// ---------------------- VALIDATOR ----------------------
class BookingValidator {

    public static void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for " + r.getRoomType());
        }
    }
}

// ---------------------- BOOKING SERVICE ----------------------
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation r) {

        try {
            // Validate first (fail-fast)
            BookingValidator.validate(r, inventory);

            // Allocate room
            inventory.reduceRoom(r.getRoomType());

            System.out.println("Booking CONFIRMED for " + r.getGuestName());

        } catch (InvalidBookingException e) {
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay v9.1 =====");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.processBooking(new Reservation("Alice", "Single Room"));

        // Invalid room type
        service.processBooking(new Reservation("Bob", "Luxury Room"));

        // Empty name
        service.processBooking(new Reservation("", "Double Room"));

        // No availability case
        service.processBooking(new Reservation("Charlie", "Single Room"));
        service.processBooking(new Reservation("David", "Single Room")); // should fail

        System.out.println("\nSystem remains stable after errors.");
    }
}