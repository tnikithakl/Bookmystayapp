import java.util.*;

class UseCase9Validation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Lokesh", "Single"));
        queue.addRequest(new Reservation("", "Double"));
        queue.addRequest(new Reservation("Anita", "Suite"));
        queue.addRequest(new Reservation("Rahul", "Luxury"));

        BookingService service = new BookingService(inventory);

        while (!queue.isEmpty()) {
            Reservation r = queue.processNextRequest();
            try {
                service.processReservation(r);
            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();
    }
}

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

class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 1);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return availability.containsKey(type) && availability.get(type) > 0;
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingService {

    private RoomInventory inventory;
    private Set<String> allocatedIds = new HashSet<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {

        validate(r);

        String type = r.getRoomType();

        if (!inventory.isAvailable(type)) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        String id = generateId(type);

        allocatedIds.add(id);
        inventory.decrement(type);

        System.out.println("Confirmed: " + r.getGuestName() + " -> " + id);
    }

    private void validate(Reservation r) {

        if (r == null) {
            throw new InvalidBookingException("Reservation cannot be null");
        }

        if (r.getGuestName() == null || r.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name is invalid");
        }

        if (r.getRoomType() == null || r.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type is required");
        }

        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }
    }

    private String generateId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + counter++;
        } while (allocatedIds.contains(id));
        return id;
    }
}

class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
