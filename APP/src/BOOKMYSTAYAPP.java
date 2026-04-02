/**
 * ---------------------------------------------------------
 * MAIN CLASS - UseCase4RoomSearch
 * ---------------------------------------------------------
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * Demonstrates how guests can view available rooms
 * without modifying inventory data.
 */

import java.util.HashMap;
import java.util.Map;

class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("Room Search\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room definitions
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Search service
        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - RoomSearchService
 * ---------------------------------------------------------
 */
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Single Room
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single") + "\n");
        }

        // Double Room
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double") + "\n");
        }

        // Suite Room
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite"));
        }
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - RoomInventory
 * ---------------------------------------------------------
 */
class RoomInventory {

    private HashMap<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();

        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return availabilityMap;
    }
}

/**
 * ---------------------------------------------------------
 * ABSTRACT CLASS - Room
 * ---------------------------------------------------------
 */
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int beds, int size, double price) {
        this.numberOfBeds = beds;
        this.squareFeet = size;
        this.pricePerNight = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - SingleRoom
 * ---------------------------------------------------------
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - DoubleRoom
 * ---------------------------------------------------------
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - SuiteRoom
 * ---------------------------------------------------------
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}