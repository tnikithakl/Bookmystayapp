import java.io.*;
import java.util.*;

class UseCase12Persistence {

    public static void main(String[] args) {

        String file = "system_state.dat";

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load(file);

        if (state == null) {
            state = new SystemState();
            state.inventory.set("Single", 2);
            state.inventory.set("Double", 1);

            state.history.add(new Reservation("S1", "Lokesh", "Single"));
            state.history.add(new Reservation("D2", "Rahul", "Double"));
        }

        System.out.println("Current State:");
        state.inventory.display();
        state.history.display();

        persistence.save(state, file);

        System.out.println("\nState saved. Restart to verify recovery.");
    }
}

class SystemState implements Serializable {
    RoomInventory inventory = new RoomInventory();
    BookingHistory history = new BookingHistory();
}

class Reservation implements Serializable {
    private String id;
    private String guest;
    private String type;

    public Reservation(String id, String guest, String type) {
        this.id = id;
        this.guest = guest;
        this.type = type;
    }

    public String getType() { return type; }

    @Override
    public String toString() {
        return id + " | " + guest + " | " + type;
    }
}

class RoomInventory implements Serializable {

    private Map<String, Integer> map = new HashMap<>();

    public void set(String type, int count) {
        map.put(type, count);
    }

    public void display() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingHistory implements Serializable {

    private List<Reservation> list = new ArrayList<>();

    public void add(Reservation r) {
        list.add(r);
    }

    public void display() {
        System.out.println("\nBooking History:");
        for (Reservation r : list) {
            System.out.println(r);
        }
    }
}

class PersistenceService {

    public void save(SystemState state, String file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(state);
        } catch (Exception e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public SystemState load(String file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("No valid saved state found. Starting fresh.");
            return null;
        }
    }
}