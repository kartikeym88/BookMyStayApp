import java.util.*;

/**
 * Book My Stay Application
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates queue processing, unique allocation using Set,
 * and synchronized inventory updates.
 *
 * @author Kartikey
 * @version 6.1
 */

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

// ---------------------- BOOKING QUEUE ----------------------
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // removes from queue
    }

    public boolean isEmpty() {
        return queue.isEmpty();
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

    public boolean reduceRoom(String type) {
        int available = getAvailability(type);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

// ---------------------- BOOKING SERVICE ----------------------
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs per type
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.replace(" ", "").toUpperCase() + "_" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Process queue
    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();

            String type = request.getRoomType();
            String guest = request.getGuestName();

            System.out.println("\nProcessing request for " + guest);

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique ID
                String roomId = generateRoomId(type);

                // Store in set (uniqueness enforced)
                allocatedRooms.putIfAbsent(type, new HashSet<>());
                allocatedRooms.get(type).add(roomId);

                // Reduce inventory (atomic step)
                inventory.reduceRoom(type);

                System.out.println("Booking CONFIRMED for " + guest);
                System.out.println("Assigned Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + guest + " (No rooms available)");
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");

        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay v6.1 =====");

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService service = new BookingService(inventory);

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // Process bookings
        service.processBookings(queue);

        // Show results
        service.displayAllocations();
        inventory.displayInventory();
    }
}