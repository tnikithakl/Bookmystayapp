import java.util.*;

class UseCase10Cancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);
        CancellationService cancellationService = new CancellationService(inventory, bookingService);

        String r1 = bookingService.book("Lokesh", "Single");
        String r2 = bookingService.book("Rahul", "Double");

        System.out.println("\nInventory After Booking:");
        inventory.displayInventory();

        cancellationService.cancel(r1);

        System.out.println("\nInventory After Cancellation:");
        inventory.displayInventory();

        cancellationService.cancel("INVALID_ID");
    }
}

class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 1);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void increment(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingService {

    private RoomInventory inventory;
    private Map<String, String> reservationMap = new HashMap<>();
    private Set<String> allocatedIds = new HashSet<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public String book(String guest, String type) {

        if (!inventory.isAvailable(type)) {
            System.out.println("No rooms available for " + type);
            return null;
        }

        String id = generateId(type);

        reservationMap.put(id, type);
        allocatedIds.add(id);
        inventory.decrement(type);

        System.out.println("Booked: " + guest + " -> " + id);
        return id;
    }

    public boolean exists(String reservationId) {
        return reservationMap.containsKey(reservationId);
    }

    public String getRoomType(String reservationId) {
        return reservationMap.get(reservationId);
    }

    public void remove(String reservationId) {
        reservationMap.remove(reservationId);
        allocatedIds.remove(reservationId);
    }

    private String generateId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + counter++;
        } while (allocatedIds.contains(id));
        return id;
    }
}

class CancellationService {

    private RoomInventory inventory;
    private BookingService bookingService;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingService bookingService) {
        this.inventory = inventory;
        this.bookingService = bookingService;
    }

    public void cancel(String reservationId) {

        if (reservationId == null || !bookingService.exists(reservationId)) {
            System.out.println("Invalid cancellation request for ID: " + reservationId);
            return;
        }

        String type = bookingService.getRoomType(reservationId);

        rollbackStack.push(reservationId);

        inventory.increment(type);

        bookingService.remove(reservationId);

        System.out.println("Cancelled: " + reservationId + " | Restored " + type);
    }
}