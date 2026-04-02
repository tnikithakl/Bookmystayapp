/**
 * ---------------------------------------------------------
 * MAIN CLASS - UseCase2HotelBookingApp
 * ---------------------------------------------------------
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Includes:
 * - Abstract class Room
 * - Concrete class SingleRoom
 * - Main method to test functionality
 */

class UseCase2HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("System initialized successfully.\n");

        // Create a Single Room object
        Room room = new SingleRoom();

        // Display room details
        room.displayRoomDetails();
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

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sq.ft");
        System.out.println("Price per night: ₹" + pricePerNight);
    }
}

/**
 * ---------------------------------------------------------
 * CLASS - SingleRoom
 * ---------------------------------------------------------
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 1500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("=== Single Room ===");
        super.displayRoomDetails();
    }
}
