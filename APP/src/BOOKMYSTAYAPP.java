/**
 * ---------------------------------------------------------
 * MAIN CLASS - UseCase3InventorySetup
 * ---------------------------------------------------------
 *
 * Use Case 3: Centralized Inventory Management (v3.1)
 *
 * Description:
 * Demonstrates how room availability is managed using
 * a centralized HashMap instead of scattered variables.
 */

import java.util.HashMap;
import java.util.Map;

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Welcome to Hotel Booking System");
        System.out.println("Initializing Room Inventory...\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        inventory.displayInventory();

        // Simulate updates
        System.out.println("\nUpdating inventory...\n");

        inventory.updateAvailability("SingleRoom", -1); // booking
        inventory.updateAvailability("DoubleRoom", +2); // added rooms

        // Display updated inventory
        inventory.displayInventory();
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - RoomInventory
 * ---------------------------------------------------------
 *
 * Responsible for managing room availability using HashMap.
 */
class RoomInventory {

    private HashMap<String, Integer> availabilityMap;

    /**
     * Constructor initializes inventory with default values
     */
    public RoomInventory() {
        availabilityMap = new HashMap<>();

        // Initial room counts
        availabilityMap.put("SingleRoom", 5);
        availabilityMap.put("DoubleRoom", 3);
        availabilityMap.put("SuiteRoom", 2);
    }

    /**
     * Get availability of a specific room type
     */
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Update availability (positive = add, negative = book/remove)
     */
    public void updateAvailability(String roomType, int change) {

        int current = availabilityMap.getOrDefault(roomType, 0);
        int updated = current + change;

        if (updated < 0) {
            System.out.println("Cannot reduce below 0 for " + roomType);
            return;
        }

        availabilityMap.put(roomType, updated);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {
        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
