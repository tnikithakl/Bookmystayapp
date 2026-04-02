import java.util.*;

public class UseCase7AddOnServices {

    public static void main(String[] args) {

        String res1 = "S1";
        String res2 = "D2";

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("Airport Pickup", 1200));

        manager.addService(res2, new AddOnService("Extra Bed", 800));

        manager.displayServices(res1);
        manager.displayServices(res2);

        System.out.println(res1 + " -> ₹" + manager.calculateTotal(res1));
        System.out.println(res2 + " -> ₹" + manager.calculateTotal(res2));
    }
}

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

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayServices(String reservationId) {

        System.out.println("\nServices for " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }

    public double calculateTotal(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (AddOnService s : services) {
            total += s.getCost();
        }

        return total;
    }
}
