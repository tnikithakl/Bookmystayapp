/**
 * ---------------------------------------------------------
 * MAIN CLASS - UseCase6BookingEngine
 * ---------------------------------------------------------
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 */

import java.util.*;

public class UseCase6BookingEngine {

    public static void main(String[] args) {

        System.out.println("Booking Engine Started\n");

        // Step 1: Inventory
        RoomInventory inventory = new RoomInventory();

        // Step 2: Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Lokesh", "Single"));
        queue.addRequest(new Reservation("Rahul", "Double"));
        queue.addRequest(new Reservation("Anita", "Suite"));
        queue.addRequest(new Reservation("Priya", "Single"));
        queue.addRequest(new Reservation("Karan", "Single")); // extra test

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(inventory);

        System.out.println("\nProcessing Bookings...\n");

        while (!queue.isEmpty()) {
            Reservation r = queue.processNextRequest();
            bookingService.processReservation(r);
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();

        System.out.println("\nAllocated Rooms:");
        bookingService.displayAllocations();
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - Reservation
 * ---------------------------------------------------------
 */
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

/**
 * ---------------------------------------------------------
 * CLASS - BookingRequestQueue
 * ---------------------------------------------------------
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation processNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - RoomInventory
 * ---------------------------------------------------------
 */
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - BookingService
 * ---------------------------------------------------------
 */
class BookingService {

    private RoomInventory inventory;

    // Map<RoomType, Set<RoomIDs>>
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set for uniqueness
    private Set<String> allRoomIds = new HashSet<>();

    private int idCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        System.out.println("Processing: " + r.getGuestName() + " -> " + type);

        // Check availability
        if (!inventory.isAvailable(type)) {
            System.out.println("❌ No rooms available for " + type + "\n");
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(type);

        // Allocate
        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomId);

        allRoomIds.add(roomId);

        // Update inventory (atomic step)
        inventory.decrement(type);

        System.out.println("✅ Booking Confirmed! Room ID: " + roomId + "\n");
    }

    private String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + idCounter++;
        } while (allRoomIds.contains(id)); // safety check
        return id;
    }

    public void displayAllocations() {
        for (Map.Entry<String, Set<String>> e : allocatedRooms.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}