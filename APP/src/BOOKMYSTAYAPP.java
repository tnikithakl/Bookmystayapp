import java.util.*;

class UseCase11ConcurrentBooking {

    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService service = new BookingService(inventory);

        queue.addRequest(new Reservation("Lokesh", "Single"));
        queue.addRequest(new Reservation("Rahul", "Single"));
        queue.addRequest(new Reservation("Anita", "Single"));
        queue.addRequest(new Reservation("Priya", "Single"));

        Runnable task = new BookingProcessor(queue, service);

        Thread t1 = new Thread(task, "T1");
        Thread t2 = new Thread(task, "T2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();

        System.out.println("\nAllocations:");
        service.displayAllocations();
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation poll() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
    }

    public synchronized boolean isAvailable(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public synchronized void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public synchronized void displayInventory() {
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingService {

    private RoomInventory inventory;
    private Set<String> allocated = new HashSet<>();
    private Map<String, String> allocations = new HashMap<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void process(Reservation r) {

        synchronized (this) {

            String type = r.getRoomType();

            if (!inventory.isAvailable(type)) {
                System.out.println(Thread.currentThread().getName() +
                        " ❌ No room for " + r.getGuestName());
                return;
            }

            String id = generateId(type);

            allocated.add(id);
            allocations.put(id, r.getGuestName());

            inventory.decrement(type);

            System.out.println(Thread.currentThread().getName() +
                    " ✅ " + r.getGuestName() + " -> " + id);
        }
    }

    private String generateId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + counter++;
        } while (allocated.contains(id));
        return id;
    }

    public void displayAllocations() {
        for (Map.Entry<String, String> e : allocations.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

class BookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private BookingService service;

    public BookingProcessor(BookingRequestQueue queue, BookingService service) {
        this.queue = queue;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.poll();
            }

            if (r != null) {
                service.process(r);
            }
        }
    }
}