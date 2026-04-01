import java.util.*;

/**
 * Book My Stay Application
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates mapping of reservation IDs to multiple services
 * without modifying booking or inventory logic.
 *
 * @author Kartikey
 * @version 7.1
 */

// ---------------------- SERVICE MODEL ----------------------
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

// ---------------------- SERVICE MANAGER ----------------------
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName() + " to " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservationId);
            return;
        }

        System.out.println("\nServices for " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " (₹" + s.getCost() + ")");
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// ---------------------- MAIN ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay v7.1 =====");

        // Simulated reservation IDs (from Use Case 6)
        String res1 = "SINGLEROOM_A123";
        String res2 = "SUITEROOM_B456";

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("Airport Pickup", 1200));
        manager.addService(res2, new AddOnService("Spa Access", 2000));

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" + manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for " + res2 + ": ₹" + manager.calculateTotalCost(res2));

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}