import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for centralized inventory control.
 *
 * @author Kartikey
 * @version 3.1
 */

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability safely
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Error: Not enough rooms available for " + roomType);
        } else {
            inventory.put(roomType, updated);
        }
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Main Class (must match file name)
public class BookMyStayApp {

    public static void main(String[] args) {

        String appName = "Book My Stay";
        String version = "v3.1";

        System.out.println("=======================================");
        System.out.println("   " + appName + " " + version);
        System.out.println("=======================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Show initial inventory
        inventory.displayInventory();

        // Simulate booking
        System.out.println("\nBooking 2 Single Rooms...");
        inventory.updateAvailability("Single Room", -2);

        // Simulate cancellation
        System.out.println("Cancelling 1 Double Room...");
        inventory.updateAvailability("Double Room", +1);

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication execution completed.");
    }
}